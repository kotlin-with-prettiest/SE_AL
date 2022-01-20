package com.example.se_al.login

import android.content.Context
import com.example.se_al.data.UserDatabase
import com.example.se_al.data.user.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup

object Login {
    fun login(id: String, pw: String, context: Context) {
        doAsync {
            val uisCookies = uisLogin(id, pw)

            val loginResponse = Jsoup.connect("https://portal.sejong.ac.kr/jsp/login/bbfrmv3.jsp")
                .cookies(uisCookies)
                .ignoreContentType(true)
                .get();

            val location = loginResponse.toString().split("location")[1].split("\"")[1]
            val session = getSession(location)

            callApi(location, session, id, pw, context)
        }
    }

    private fun uisLogin(id: String, pw: String): MutableMap<String, String> {
        val loginResponse = Jsoup.connect("https://portal.sejong.ac.kr/jsp/login/login_action.jsp")
            .header("Referer", "https://portal.sejong.ac.kr")
            .header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0")
            .data("id", id)
            .data("password", pw)
            .data("rtUrl", "blackboard.sejong.ac.kr")
            .method(Connection.Method.POST)
            .execute()

        return loginResponse.cookies()
    }

    private fun getSession(location: String): Map<String, String> {
        val authLink = Jsoup.connect(location).method(Connection.Method.POST).execute()

        return authLink.cookies()
    }

    private fun callApi(location: String, session: Map<String, String>, id: String, pw: String, context: Context) {
        val userId = getUserId(location)

        getAllCourses(userId, session, id, pw, context)
    }

    private fun getUserId(location: String): String {
        val doc = Jsoup.connect(location).get()

        return doc.select("script#initial-context-script").toString().split("id\":\"")[2].split("\"")[0].replace(" ", "")
    }

    private fun getAllCourses(userId: String, session: Map<String, String>, id: String, pw: String, context: Context) {
        val url = "https://blackboard.sejong.ac.kr/learn/api/v1/users/$userId/memberships?expand=course.effectiveAvailabilty"

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
            }
        }

        insertUser(userId, "최옐인", id, pw, coursesId, context)
    }

    private fun insertUser(userId: String, name:String, bbId:String, bbPassword:String, courses: List<String>, context: Context) {
        try {
            var newUser = User(userId, name, bbId, bbPassword, courses)
            val db = UserDatabase.getInstance(context)

            CoroutineScope(Dispatchers.IO).launch { // 비동기
                db!!.userDao().insert(newUser)
            }
        } catch (e: java.lang.Exception) {
            println("Insert 에러 - $e")
        }
    }
}