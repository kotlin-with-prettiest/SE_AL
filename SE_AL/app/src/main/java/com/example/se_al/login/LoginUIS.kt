package com.example.se_al.login

import android.util.Log
import org.jsoup.Connection
import org.jsoup.Jsoup

/*
* uis 로그인
* 로그인 성공시 세션 유지를 위한 쿠키를 가져옴
* 로그인 실패시 여기 말고 다음 단계에서 실패 결과 리턴함
* ==로그인 성공 여부에 관계 없이 무조건 쿠키 가져오겠다는 말
* */
fun uisLogin(id:String, password:String): MutableMap<String, String> {
    val loginResponse = Jsoup.connect("https://portal.sejong.ac.kr/jsp/login/login_action.jsp")
        .header("Referer", "https://portal.sejong.ac.kr")
        .header("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0")
        .data("id", id)
        .data("password", password)
        .data("rtUrl", "blackboard.sejong.ac.kr")
        .method(Connection.Method.POST)
        .execute()

    return loginResponse.cookies()
}

/*
* 블랙보드 로그인 (앱 초기 로그인 용)
* uis 로그인 세션으로 블랙보드에 로그인
* 로그인 성공: true 리턴
* 로그인 실패: false 리턴*/
fun firstBlackBoardLogin():Boolean{
    val uisCookies = uisLogin("","")

    val loginResponse = Jsoup.connect("https://portal.sejong.ac.kr/jsp/login/bbfrmv3.jsp")
        .cookies(uisCookies)
        .ignoreContentType(true)
        .get();

    return loginResponse.select("script").toString() != "<script>alert('비정상적인 접근입니다.');window.close();</script>"
}