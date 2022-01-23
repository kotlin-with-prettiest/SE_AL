package com.example.se_al.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.*
import java.util.concurrent.TimeUnit

class NotiWorker (appContext: Context, parameters: WorkerParameters) :
    CoroutineWorker(appContext, parameters) {

    companion object {
        const val WORK_NAME = "Notification Work"
    }

    // 만든 채널 정보를 시스템에 등록
    val notificationManager: NotificationManager =
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        try{

            noti();

            val oneTimeWorkRequest =
            OneTimeWorkRequestBuilder<NotiWorker>().setInitialDelay(1, TimeUnit.MINUTES).build()

            WorkManager.getInstance(applicationContext)
                .enqueueUniqueWork(NotiWorker.WORK_NAME, ExistingWorkPolicy.REPLACE, oneTimeWorkRequest)

        } catch (e: Exception) {
            Result.retry()
        }

        return Result.success()
    }

    private fun noti() {
        var builder = NotificationCompat.Builder(applicationContext, "MY_channel")
            //.setSmallIcon(R.drawable.)
            .setContentTitle("이게 성공인가")
            .setContentText("설명인것이다.")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 오레오 버전 이후에는 알림을 받을 때 채널이 필요
            val channel_id = "MY_channel" // 알림을 받을 채널 id 설정
            val channel_name = "채널이름" // 채널 이름 설정
            val descriptionText = "채널 설명입니다" // 채널 설명글 설정
            val importance = NotificationManager.IMPORTANCE_DEFAULT // 알림 우선순위 설정
            val channel = NotificationChannel(channel_id, channel_name, importance).apply {
                description = descriptionText
            }


            notificationManager.createNotificationChannel(channel)

            // 알림 표시: 알림의 고유 ID(ex: 1002), 알림 결과
            notificationManager.notify(1002, builder.build())

            Log.d("성공", "성공의 맛")
        }
    }
}