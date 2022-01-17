package com.example.se_al

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.se_al.data.UserDatabase
import com.example.se_al.data.announcement.Announcement
import com.example.se_al.data.assignment.Assignment
import com.example.se_al.data.calendar.Calendar
import com.example.se_al.data.children_content.ChildrenContent
import com.example.se_al.data.content.Content
import com.example.se_al.data.course.Course
import com.example.se_al.data.exam.Exam
import com.example.se_al.data.lecture.Lecture
import com.example.se_al.data.menu.Menu
import com.example.se_al.data.sub_lecture.SubLecture
import com.example.se_al.data.user.User
import com.example.se_al.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.lang.Exception

val TAG = "main"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val id = ""
    private val password = ""
    private val rtUrl = "blackboard.sejong.ac.kr"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_class_list, R.id.navigation_home_calendar, R.id.navigation_setting))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        login()
        //noti()

    }

//    fun CloseKeyboard() {
//        var view = this.currentFocus
//
//        if (view != null) {
//            val inputMethodManager =
//                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//        }
//        Log.d(TAG, "hide keyboard")
//    }

    object flag {
        var home_flag : Int = 0
    }

    private fun login() {
        doAsync {
            val uisCookies = uisLogin()

            val loginResponse = Jsoup.connect("https://portal.sejong.ac.kr/jsp/login/bbfrmv3.jsp")
                .cookies(uisCookies)
                .ignoreContentType(true)
                .get();

            val location = loginResponse.toString().split("location")[1].split("\"")[1]
            val session = getSession(location)

            callApi(location, session)
        }
    }

    private fun uisLogin(): MutableMap<String, String> {
        val loginResponse = Jsoup.connect("https://portal.sejong.ac.kr/jsp/login/login_action.jsp")
            .header("Referer", "https://portal.sejong.ac.kr")
            .header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0")
            .data("id", id)
            .data("password", password)
            .data("rtUrl", rtUrl)
            .method(Connection.Method.POST)
            .execute()

        return loginResponse.cookies()
    }

    private fun getSession(location: String): Map<String, String> {
        val authLink = Jsoup.connect(location).method(Connection.Method.POST).execute()

        return authLink.cookies()
    }

    private fun callApi(location: String, session: Map<String, String>) {
        val userId = getUserId(location)

        println(userId)

        getAllCourses(userId, session)
    }

    private fun getUserId(location: String): String {
        val doc = Jsoup.connect(location).get()

        return doc.select("script#initial-context-script").toString().split("id\":\"")[2].split("\"")[0].replace(" ", "")
    }

    private fun getAllCourses(userId: String, session: Map<String, String>) {
        //11//val textView: TextView = findViewById<TextView>(R.id.textView)
        val url = "https://blackboard.sejong.ac.kr/learn/api/v1/users/$userId/memberships?expand=course.effectiveAvailabilty"

        //_66176_1

        val allCoursesResponse = Jsoup.connect(url)
            .cookies(session)
            .ignoreContentType(true)
            .get();

        val jsonObject = JSONObject(allCoursesResponse.body().text())
        val resultsArray = jsonObject.getJSONArray("results")
        var stringForArray = ""

        var coursesId = mutableListOf<String>()

        for (i in 0 until resultsArray.length()) {
            val iObject = resultsArray.getJSONObject(i)

            val course = iObject.getJSONObject("course")
            val courseId = iObject.getString("courseId")
            val displayName = course.getString("displayName")

            var termId = "none"

            if (course.has("termId")) {
                termId = course.getString("termId")
            }

            var termName = "none"
            var termStartDate = "none"
            var termEndDate = "none"

            if (course.has("term")) {
                termName = course.getJSONObject("term").getString("name")
                termStartDate = course.getJSONObject("term").getString("startDate")
                termEndDate = course.getJSONObject("term").getString("endDate")
            }

            stringForArray += "${i + 1}번 \n courseId : $courseId \n 과목명: $displayName \n termId: $termId \n " +
                    "학기명: $termName \n 학기시작일: $termStartDate \n 학기종료일: $termEndDate \n\n\n\n"

            if (termId == "_71_1" && !displayName.contains("온라인학습법") && !displayName.contains("폭력예방교육")) { // 2021-2
                coursesId.add(courseId)
                doAsync {
                    getMenus(courseId, displayName, session) // 해당 코스에 대한 메뉴 insert
                }
                insertCourse(courseId, displayName, termId, termName)
            }
        }

        insertUser(userId, "최예린", "bbid", "bbpw", coursesId) // ** 사용자 insert


        runOnUiThread {
            //11//textView.text = stringForArray
        }

        doAsync {
            getCalendars(session)
        }
    }

    private fun getMenus(courseId:String, courseName: String, session: Map<String, String>) { // 코스마다 실행
        // 메뉴 GET / INSERT

        val url = "https://blackboard.sejong.ac.kr/learn/api/public/v1/courses/$courseId/contents"

        val content = Jsoup.connect(url)
            .cookies(session)
            .ignoreContentType(true)
            .get();

        val jsonObject = JSONObject(content.body().text())
        val resultsArray = jsonObject.getJSONArray("results")


        for (i in 0 until resultsArray.length()) { // 특정 코스에 있는 메뉴 개수만큼 실행
            val iObject = resultsArray.getJSONObject(i)

            val contentId = iObject.getString("id") //menu_id
            val title = iObject.getString("title")

            // TODO: 퀴즈
            if (title.contains("강의") || title.contains("과제") || title.contains("시험") || title.contains("영상") || title.contains("Lectures") || title.contains("Exam") || title.contains("Assignments"))
            {
                insertMenu(courseId, courseName, title, contentId)
                doAsync {
                    getContent(title, courseId, contentId, session)
                }
                // TODO: 과제
                if (title.contains("과제") || title.contains("Assignments")) {
                    doAsync {
                        getAssignment(courseId, courseName, contentId, session)
                    }
                }
                // TODO: 시험
                if (title.contains("시험") || title.contains("Exam")) {
                    doAsync {
                        getExam(courseId, courseName, contentId, session)
                    }
                }
                //  TODO: 강의자료 및 학습
                if (title.contains("강의") || title.contains("Lectures")) {
                    doAsync {
                        getLecture(courseId, courseName, contentId, session)
                    }
                }
            }
        }

        doAsync {
            getAnnouncement(session)
        }
    }

    private fun getContent(menuName: String, courseId: String, contentId: String, session: Map<String, String>) {
        /*
         * 강의 자료 및 학습
         * -----
         *
         * 공지
         * 1주차
         * 2주차
         * 3주차
         *
         * */

        val url = "https://blackboard.sejong.ac.kr/learn/api/public/v1/courses/$courseId/contents/$contentId/children"

        val content = Jsoup.connect(url)
            .cookies(session)
            .ignoreContentType(true)
            .get();

        val jsonObject = JSONObject(content.body().text())
        val resultsArray = jsonObject.getJSONArray("results")

        for (i in 0 until resultsArray.length()) {
            val iObject = resultsArray.getJSONObject(i)


            val id = iObject.getString("id") // 이게 content_id (공지, 1주차 ..)
            val title = iObject.getString("title")
            val parentId = iObject.getString("parentId")

            insertContent(id, parentId, menuName, title)

            doAsync {
                getContentChildren(courseId, id, session)
            }
        }
    }

    private fun getContentChildren(courseId: String, parentId: String, session: Map<String, String>) {
        //강의 1주차

        val url = "https://blackboard.sejong.ac.kr/learn/api/public/v1/courses/$courseId/contents/$parentId/children"

        val content = Jsoup.connect(url)
            .cookies(session)
            .ignoreContentType(true)
            .get();

        val jsonObject = JSONObject(content.body().text())
        val resultsArray = jsonObject.getJSONArray("results")

        for (i in 0 until resultsArray.length()) {
            val iObject = resultsArray.getJSONObject(i)


            val contentId = iObject.getString("id")
            val title = iObject.getString("title")
            val created = iObject.getString("created")

            insertChildContent(parentId, contentId, title, created)
        }
    }

//    fun noti() {
//        var button = findViewById<Button>(R.id.noti)
//        button.setOnClickListener {
//            var builder = NotificationCompat.Builder(this, "MY_channel")
//                .setSmallIcon(R.drawable.ic_noti)
//                .setContentTitle("개짱나")
//                .setContentText("미친코틀린")
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 오레오 버전 이후에는 알림을 받을 때 채널이 필요
//                val channel_id = "MY_channel" // 알림을 받을 채널 id 설정
//                val channel_name = "채널이름" // 채널 이름 설정
//                val descriptionText = "설명글" // 채널 설명글 설정
//                val importance = NotificationManager.IMPORTANCE_DEFAULT // 알림 우선순위 설정
//                val channel = NotificationChannel(channel_id, channel_name, importance).apply {
//                    description = descriptionText
//                }
//
//                // 만든 채널 정보를 시스템에 등록
//                val notificationManager: NotificationManager =
//                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                notificationManager.createNotificationChannel(channel)
//
//                // 알림 표시: 알림의 고유 ID(ex: 1002), 알림 결과
//                notificationManager.notify(1002, builder.build())
//            }
//        }
//    }

    fun insertUser(userId: String, name:String, bbId:String, bbPassword:String, courses: List<String>) {
        try {
            var newUser = User(userId, name, bbId, bbPassword, courses)
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.userDao().insert(newUser)
                printAllUsers()
            }
        } catch (e: Exception) {
            println("Insert 에러 - $e")
        }
    }

    fun deleteUser() {
        try {
            var userName = "전선영"
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.userDao().deleteUserByName(userName)
                printAllUsers()
            }

        } catch (e: Exception) {
            println("Delete 에러 - $e")
        }
    }

    fun printAllUsers() {
        try {
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                println(db!!.userDao().getAll().toString())
            }

        } catch (e: Exception) {
            println("Print 에러 - $e")
        }
    }

    fun insertCourse(courseId: String, name: String, termId: String, termName: String) {
        try {
            var newCourse = Course(courseId, name, termId, termName)
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.courseDao().insert(newCourse)
            }
        } catch (e: Exception) {
            println("Insert (course) 에러 - $e")
        }
    }

    fun printAllCourses() {
        try {
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                println(db!!.courseDao().getAll().toString())
            }

        } catch (e: Exception) {
            println("Print (course) 에러 - $e")
        }
    }

    fun insertContent(contentId: String, parentId:String, menuName:String, title: String) {
        try {
            var newContent = Content(contentId, parentId, menuName, title)
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.contentDao().insert(newContent)
            }
        } catch (e: Exception) {
            println("Insert (content) 에러 - $e")
        }
    }

    fun printAllContents() {
        try {
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                println(db!!.contentDao().getAll().toString())
            }

        } catch (e: Exception) {
            println("Print (content) 에러 - $e")
        }
    }

    fun insertChildContent(parentId: String, contentId: String, title: String, created: String) {
        try {
            var newContent = ChildrenContent(parentId, contentId, title, created)
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.childrenDao().insert(newContent)
            }
        } catch (e: Exception) {
            println("Insert (child content) 에러 - $e")
        }
    }


    fun insertMenu(courseId: String, courseName: String, name: String, contentId: String) {
        try {
            var newMenu = Menu(courseId, courseName, name, contentId)
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.menuDao().insert(newMenu)
            }
        } catch (e: Exception) {
            println("Insert (menu) 에러 - $e")
        }
    }


    fun allCourses(): List<Course> {
        val db = UserDatabase.getInstance(applicationContext)
        lateinit var courses: List<Course>

        CoroutineScope(Dispatchers.IO).launch { // 비동기
            courses = db!!.courseDao().getAll()
        }

        return courses
    }

    fun getAnnouncement(session: Map<String, String>) {
        val courses = allCourses()

        for (course in courses) {
            val url = "https://blackboard.sejong.ac.kr/learn/api/public/v1/courses/${course.course_id}/announcements"

            val content = Jsoup.connect(url)
                .cookies(session)
                .ignoreContentType(true)
                .get();

            val jsonObject = JSONObject(content.body().text())
            val resultsArray = jsonObject.getJSONArray("results")

            for (i in 0 until resultsArray.length()) {
                val iObject = resultsArray.getJSONObject(i)

                val courseId = course.course_id
                val courseName = course.course_name
                val announcementId = iObject.getString("id")
                val title = iObject.getString("title")
                val body = iObject.getString("body")

                insertAnnouncement(courseId, courseName, announcementId, title, body)
            }
        }
    }

    fun insertAnnouncement(courseId: String, courseName: String, announcementId: String, title: String, body: String) {
        try {
            var newAnnouncement = Announcement(courseId, courseName, announcementId, title, body)
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.announcementDao().insert(newAnnouncement)
            }
        } catch (e: Exception) {
            println("Insert (menu) 에러 - $e")
        }
    }

    fun getAssignment(courseId: String, courseName: String, menuId: String, session: Map<String, String>) {
        val url =
            "https://blackboard.sejong.ac.kr/learn/api/public/v1/courses/$courseId/contents/$menuId/children"


        val content = Jsoup.connect(url)
            .cookies(session)
            .ignoreContentType(true)
            .get();

        val jsonObject = JSONObject(content.body().text())
        val resultsArray = jsonObject.getJSONArray("results")

        for (i in 0 until resultsArray.length()) { // 한 코스의 과제 개수만큼
            val iObject = resultsArray.getJSONObject(i)

            val title = iObject.getString("title")
            var body: String? = null

            if(iObject.has("body")) {
                body = iObject.getString("body")
            }

            val assignmentId = iObject.getString("id")
            var source: JSONObject? = null
            var sourceId: String? = null
            var created = iObject.getString("created")

            if (iObject.has("contentHandler")) {
                source = iObject.getJSONObject("contentHandler")
                if (source.has("gradeColumnId")) {
                    sourceId = source.getString("gradeColumnId")
                }
            }

            var dueDate: List<String> = listOf("none", "none")
            if (sourceId != null)
            {
                dueDate = calendarQuery(sourceId)
            }
            insertAssignment(courseId, courseName, title, body, null, assignmentId, sourceId, dueDate[0], dueDate[1], created)
        }
    }


    fun getCalendars(session: Map<String, String>) {
        val url =
            "https://blackboard.sejong.ac.kr/learn/api/v1/calendars/calendarItems?since=2021-07-01T15:00:00.000Z&until=2022-02-05T15:00:00.000Z"

        val content = Jsoup.connect(url)
            .cookies(session)
            .ignoreContentType(true)
            .get();

        val jsonObject = JSONObject(content.body().text())
        val resultsArray = jsonObject.getJSONArray("results")

        for (i in 0 until resultsArray.length()) {
            val iObject = resultsArray.getJSONObject(i)

            val startDate = iObject.getString("startDate")
            val endDate = iObject.getString("endDate")
            val title = iObject.getString("title")
            val event = iObject.getJSONObject("dynamicCalendarItemProps")
            val type = event.getString("eventType")
            val sourceId = iObject.getString("itemSourceId")
            val course = iObject.getJSONObject("calendarNameLocalizable")
            val courseName = course.getString("rawValue")

            insertCalendar(sourceId, courseName, startDate, endDate, title, type)

        }
    }

    fun insertCalendar(sourceId: String, courseName: String, startDate: String, endDate: String, title: String, type: String) {
        try {
            var newCalendar = Calendar(sourceId, courseName, startDate, endDate, title, type)
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.calendarDao().insert(newCalendar)
            }
        } catch (e: Exception) {
            println("Insert (calendar) 에러 - $e")
        }
    }

    fun insertAssignment(courseId: String, courseName: String, title: String, body: String?, memo: String?, assignmentId: String, sourceId: String?, startDate: String?, endDate: String?, created: String) {
        try {
            var newAssignment = Assignment(courseId, courseName, title, body, memo, assignmentId, sourceId, startDate, endDate, created)
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.assignmentDao().insert(newAssignment)
            }
        } catch (e: Exception) {
            println("Insert (insertAssignment) 에러 - $e")
        }
    }

    fun calendarQuery(sourceId: String): List<String> {
        lateinit var startDate: String
        lateinit var endDate: String
        val db = UserDatabase.getInstance(applicationContext)

        startDate = db!!.calendarDao().getStartDate(sourceId)
        endDate = db!!.calendarDao().getEndDate(sourceId)

        return listOf(startDate, endDate)
    }

    fun getExam(courseId: String, courseName: String, menuId: String, session: Map<String, String>) {
        val url =
            "https://blackboard.sejong.ac.kr/learn/api/public/v1/courses/$courseId/contents/$menuId/children"

        val content = Jsoup.connect(url)
            .cookies(session)
            .ignoreContentType(true)
            .get();

        val jsonObject = JSONObject(content.body().text())
        val resultsArray = jsonObject.getJSONArray("results")

        for (i in 0 until resultsArray.length()) { // 한 코스의 시험 개수만큼
            val iObject = resultsArray.getJSONObject(i)

            Log.d("시험", iObject.toString())

            val title = iObject.getString("title")
            var body: String? = null

            if (iObject.has("body")) {
                body = iObject.getString("body")
            }

            val examId = iObject.getString("id")
            var source: JSONObject? = null
            var sourceId: String? = null
            var created = iObject.getString("created")

            if (iObject.has("contentHandler")) {
                source = iObject.getJSONObject("contentHandler")
                if (source.has("gradeColumnId")) {
                    sourceId = source.getString("gradeColumnId")
                }
            }

            var dueDate: List<String> = listOf("none", "none")
            if (sourceId != null) {
                dueDate = calendarQuery(sourceId)
            }
            insertExam(
                courseId,
                courseName,
                title,
                body,
                null,
                examId,
                sourceId,
                dueDate[0],
                dueDate[1],
                created
            )
        }
    }

    fun insertExam(courseId: String, courseName: String, title: String, body: String?, memo: String?, assignmentId: String, sourceId: String?, startDate: String?, endDate: String?, created: String) {
        try {
            var newExam = Exam(courseId, courseName, title, body, memo, assignmentId, sourceId, startDate, endDate, created)
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.examDao().insert(newExam)
            }
        } catch (e: Exception) {
            println("Insert (insertExam) 에러 - $e")
        }
    }

    fun getLecture(courseId: String, courseName: String, menuId: String, session: Map<String, String>) {
        val url =
            "https://blackboard.sejong.ac.kr/learn/api/public/v1/courses/$courseId/contents/$menuId/children"

        val content = Jsoup.connect(url)
            .cookies(session)
            .ignoreContentType(true)
            .get();

        val jsonObject = JSONObject(content.body().text())
        val resultsArray = jsonObject.getJSONArray("results")

        for (i in 0 until resultsArray.length()) { // 한 코스의 주차수만큼
            val iObject = resultsArray.getJSONObject(i)

            val lectureWeek = iObject.getString("title")
            val lectureId = iObject.getString("id")

            insertLecture(courseId, courseName, lectureWeek, lectureId)

            doAsync {
                getSubLecture(courseId, courseName, lectureId, session)
            }
        }
    }

    fun insertLecture(courseId: String, courseName: String, lectureWeek: String, lectureId: String) {
        try {
            var newLecture = Lecture(courseId, courseName, lectureWeek, lectureId)
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.lectureDao().insert(newLecture)
            }
        } catch (e: Exception) {
            println("Insert (insertLecture) 에러 - $e")
        }
    }

    fun getSubLecture(courseId: String, courseName: String, lectureId: String, session: Map<String, String>) {
        // courseId: _69555_1
        // lectureId: _2759746_1

        val url =
            "https://blackboard.sejong.ac.kr/learn/api/public/v1/courses/$courseId/contents/$lectureId/children"

        val content = Jsoup.connect(url)
            .cookies(session)
            .ignoreContentType(true)
            .get();

        val jsonObject = JSONObject(content.body().text())
        val resultsArray = jsonObject.getJSONArray("results")

        for (i in 0 until resultsArray.length()) {
            val iObject = resultsArray.getJSONObject(i)

            val title = iObject.getString("title")
            val subLectureId = iObject.getString("id")
            val parentId = iObject.getString("parentId")
            var body: String? = null
            val created = iObject.getString("created")

            if (iObject.has("body")) {
                body = iObject.getString("body")
            }

            insertSubLecture(courseId, courseName, title, body, null, parentId, subLectureId, null, null, created)
        }
    }

    fun insertSubLecture(courseId: String, courseName: String, title: String, body: String?, memo: String?, parentId: String, subLectureId: String, startDate: String?, endDate: String?, created: String) {
        try {
            var newSubLecture = SubLecture(courseId, courseName, title, body, memo, parentId, subLectureId, startDate, endDate, created)
            val db = UserDatabase.getInstance(applicationContext)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.subLectureDao().insert(newSubLecture)
            }
        } catch (e: Exception) {
            println("Insert (insertSubLecture) 에러 - $e")
        }
    }


}

