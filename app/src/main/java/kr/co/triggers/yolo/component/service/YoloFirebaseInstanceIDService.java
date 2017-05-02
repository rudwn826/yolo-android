package kr.co.triggers.yolo.component.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class YoloFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        super.onTokenRefresh();

        // TODO : 토큰 서버에 등록
        String token = FirebaseInstanceId.getInstance().getToken();

        // TODO : 주제 구독 필요
        // FirebaseMessaging.getInstance().subscribeToTopic("news");
    }
}
