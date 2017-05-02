package kr.co.triggers.yolo.scenario;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kr.co.triggers.yolo.domain.Artist;
import kr.co.triggers.yolo.domain.Data;
import kr.co.triggers.yolo.domain.Feed;
import kr.co.triggers.yolo.domain.Fiesta;
import kr.co.triggers.yolo.domain.Notification;
import kr.co.triggers.yolo.domain.Perform;
import kr.co.triggers.yolo.domain.Photo;
import kr.co.triggers.yolo.domain.Track;
import kr.co.triggers.yolo.domain.User;
import kr.co.triggers.yolo.network.entity.SearchEntity;
import kr.co.triggers.yolo.network.entity.TagEntity;
import okhttp3.MultipartBody;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

public class Scenario {

    public static Observable<Response<User>> login(String token){

        return Observable.create(new Observable.OnSubscribe<Response<User>>() {
            @Override
            public void call(Subscriber<? super Response<User>> subscriber) {
                User data = new User();
                data.setId(12345678);
                Response<User> user = Response.success(data);

                subscriber.onNext(user);
                subscriber.onCompleted();

                // Enroll (error 400) scenario
                //ResponseBody responseBody = ResponseBody.create(okhttp3.MediaType.parse("text/plain"), String.valueOf("hello"));
                //subscriber.onError(new HttpException(Response.error(400, responseBody)));
                //subscriber.onCompleted();
            }
        });
    }

    public static Observable<Response<List<Notification>>> mockNotification(){

        return Observable.create(new Observable.OnSubscribe<Response<List<Notification>>>(){

            @Override
            public void call(Subscriber<? super Response<List<Notification>>> subscriber) {

                List<Notification> notifications = new ArrayList<>();

                Notification notification = new Notification();
                notification.setId(1);
                notification.setMessage("Knife Party, Avicii, Galantis, Triggers, Alesso and 6 other people reacted to your post.");
                notification.setType(Notification.TYPE_EVENT_ARTIST_SUGGEST);
                notification.setCreatedAt(new Date(12345678));

                Notification notification2 = new Notification();
                notification2.setId(2);
                notification2.setMessage("Hello world2");
                notification2.setType(Notification.TYPE_EVENT_FIESTA_CHANGES);
                notification2.setCreatedAt(new Date(23456789));

                Notification notification3 = new Notification();
                notification3.setId(3);
                notification3.setMessage("Hello world3");
                notification3.setType(Notification.TYPE_EVENT_FIESTA_SUGGEST);
                notification3.setCreatedAt(new Date(34567890));

                Notification notification4 = new Notification();
                notification4.setId(4);
                notification4.setMessage("Hello world4");
                notification4.setType(Notification.TYPE_EVENT_TRACK_SUGGEST);
                notification4.setCreatedAt(new Date(45678901));

                Notification notification5 = new Notification();
                notification5.setId(5);
                notification5.setMessage("Hello world5");
                notification5.setType(Notification.TYPE_EVENT_ARTIST_SUGGEST);
                notification5.setCreatedAt(new Date(56789012));

                Notification notification6 = new Notification();
                notification6.setId(6);
                notification6.setMessage("Hello world6");
                notification6.setType(Notification.TYPE_EVENT_FIESTA_CHANGES);
                notification6.setCreatedAt(new Date(67890123));

                Notification notification7 = new Notification();
                notification7.setId(7);
                notification7.setMessage("Hello world7");
                notification7.setType(Notification.TYPE_EVENT_FIESTA_SUGGEST);
                notification7.setCreatedAt(new Date(78901234));

                Notification notification8 = new Notification();
                notification8.setId(8);
                notification8.setMessage("Hello world8");
                notification8.setType(Notification.TYPE_EVENT_TRACK_SUGGEST);
                notification8.setCreatedAt(new Date(89012345));

                notifications.add(notification);
                notifications.add(notification2);
                notifications.add(notification3);
                notifications.add(notification4);
                notifications.add(notification5);
                notifications.add(notification6);
                notifications.add(notification7);
                notifications.add(notification8);

                Response<List<Notification>> response = Response.success(notifications);

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<Response<List<Notification>>> mockNotification(long before, int count){
        return Observable.create(new Observable.OnSubscribe<Response<List<Notification>>>(){

            @Override
            public void call(Subscriber<? super Response<List<Notification>>> subscriber) {

                List<Notification> notifications = new ArrayList<>();

                Notification notification = new Notification();
                notification.setId(1);
                notification.setMessage("New world");
                notification.setType(Notification.TYPE_EVENT_ARTIST_SUGGEST);
                notification.setCreatedAt(new Date(12345678));

                Notification notification2 = new Notification();
                notification2.setId(2);
                notification2.setMessage("New world2");
                notification2.setType(Notification.TYPE_EVENT_FIESTA_CHANGES);
                notification2.setCreatedAt(new Date(23456789));

                Notification notification3 = new Notification();
                notification3.setId(3);
                notification3.setMessage("New world3");
                notification3.setType(Notification.TYPE_EVENT_FIESTA_SUGGEST);
                notification3.setCreatedAt(new Date(34567890));

                Notification notification4 = new Notification();
                notification4.setId(4);
                notification4.setMessage("New world4");
                notification4.setType(Notification.TYPE_EVENT_TRACK_SUGGEST);
                notification4.setCreatedAt(new Date(45678901));

                notifications.add(notification);
                notifications.add(notification);
                notifications.add(notification);
                notifications.add(notification);

                Response<List<Notification>> response = Response.success(notifications);
                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<Response<List<Artist>>> artistInfo(String query){

        return Observable.create(new Observable.OnSubscribe<Response<List<Artist>>>() {
            @Override
            public void call(Subscriber<? super Response<List<Artist>>> subscriber) {
                List<Artist> artists = new ArrayList<Artist>();

                Artist artist = new Artist();

                artist.setId(1);
                artist.setName("Avicii");
                artist.setLiked(true);

                Artist artist1 = new Artist();
                artist1.setId(2);
                artist1.setName("Martin Garrix");
                artist1.setLiked(true);

                Artist artist2 = new Artist();

                artist2.setId(3);
                artist2.setName("MARK J");
                artist2.setLiked(true);

                Artist artist3 = new Artist();
                artist3.setId(4);
                artist3.setName("ALPACA");
                artist3.setLiked(true);

                Artist artist4 = new Artist();

                artist4.setId(5);
                artist4.setName("TRIGGERS");
                artist4.setLiked(true);

                Artist artist5= new Artist();
                artist5.setId(6);
                artist5.setName("GIMOJJI");
                artist5.setLiked(true);

                Artist artist6 = new Artist();

                artist6.setId(1);
                artist6.setName("Avicii");
                artist6.setLiked(true);

                Artist artist7 = new Artist();
                artist7.setId(2);
                artist7.setName("Martin Garrix");
                artist7.setLiked(true);

                Artist artist8 = new Artist();

                artist8.setId(3);
                artist8.setName("MARK J");
                artist8.setLiked(true);

                Artist artist9 = new Artist();
                artist9.setId(4);
                artist9.setName("ALPACA");
                artist9.setLiked(true);

                Artist artist10 = new Artist();

                artist10.setId(5);
                artist10.setName("TRIGGERS");
                artist10.setLiked(true);

                Artist artist11= new Artist();
                artist11.setId(6);
                artist11.setName("GIMOJJI");
                artist11.setLiked(true);

                artists.add(artist);
                artists.add(artist1);
                artists.add(artist2);
                artists.add(artist3);
                artists.add(artist4);
                artists.add(artist5);
                artists.add(artist6);
                artists.add(artist7);
                artists.add(artist8);
                artists.add(artist9);
                artists.add(artist10);
                artists.add(artist11);

                Response<List<Artist>> response = Response.success(artists);

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<Response<SearchEntity>> search(String query){

        return Observable.create(new Observable.OnSubscribe<Response<SearchEntity>>(){
            @Override
            public void call(Subscriber<? super Response<SearchEntity>> subscriber) {

                List<Artist> artists = new ArrayList<>();

                Artist artist = new Artist();

                artist.setId(1);
                artist.setName("Avicii");
                artist.setLiked(true);

                Artist artist1 = new Artist();
                artist1.setId(2);
                artist1.setName("Martin Garrix");
                artist1.setLiked(true);

                artists.add(artist);
                artists.add(artist1);

                List<Data> genres;
                List<Track> tracks = new ArrayList<>();

                Track track;
                Data tag;

                // Waiting for Love

                genres = new ArrayList<>();

                tag = new Data();
                tag.setName("Electro House");
                tag.setType(Data.TYPE_GENRE);

                genres.add(tag);

                track  = new Track();

                track.setTitle("Waiting For Love");
                track.setLink("cHHLHGNpCSA");
                track.setLiked(true);
                track.setCreatedAt(new Date());
                track.setUpdatedAt(new Date());
                track.setGenres(genres);

                tracks.add(track);

                // Wake Me Up

                genres = new ArrayList<>();

                tag = new Data();
                tag.setName("Electro House");
                tag.setType(Data.TYPE_GENRE);

                genres.add(tag);
/*
                tag = new Data();
                tag.setName("Folktronica");
                tag.setType(Data.TYPE_GENRE);

                genres.add(tag);
*/
                track  = new Track();

                track.setTitle("Wake Me Up");
                track.setLink("IcrbM1l_BoI");
                track.setLiked(false);
                track.setCreatedAt(new Date());
                track.setUpdatedAt(new Date());
                track.setGenres(genres);

                tracks.add(track);

                List<Fiesta> fiestas = new ArrayList<>();

                Fiesta fiesta = new Fiesta();
                fiesta.setName("Ultra Music Festival");

                Fiesta fiesta1 = new Fiesta();
                fiesta1.setName("World DJ Festival");

                Fiesta fiesta2 = new Fiesta();
                fiesta2.setName("Spectrum Festival");

                fiestas.add(fiesta);
                fiestas.add(fiesta1);
                fiestas.add(fiesta2);

                SearchEntity searchEntity = new SearchEntity();

                searchEntity.setArtists(artists);
                searchEntity.setTracks(tracks);
                searchEntity.setFiesta(fiestas);

                Response<SearchEntity> response = Response.success(searchEntity);

                subscriber.onNext(response);
                subscriber.onCompleted();

            }
        });
    }

    public static Observable<Response<User>> getUserInfo(){

        return Observable.create(new Observable.OnSubscribe<Response<User>>(){

            @Override
            public void call(Subscriber<? super Response<User>> subscriber) {
                User data = new User();
                data.setName("Jang KyungJoo");
                data.setGender("M");
                data.setBirth(new Date(1343805819));

                Response<User> user = Response.success(data);

                subscriber.onNext(user);
                subscriber.onCompleted();

            }
        });
    }

    public static Observable<Response<TagEntity>> mockTag(long id){

        return Observable.create(new Observable.OnSubscribe<Response<TagEntity>>(){

            @Override
            public void call(Subscriber<? super Response<TagEntity>> subscriber) {
                List<Track> tracks = new ArrayList<>();

                Artist artist = new Artist();
                artist.setId(1);
                artist.setName("avichii");

                List<Data> datas = new ArrayList<>();

                Data data = new Data();
                data.setName("Electro House");
                data.setType(Data.TYPE_GENRE);

                datas.add(data);

                Track track  = new Track();

                track.setTitle("Waiting For Love");
                track.setLink("cHHLHGNpCSA");
                track.setLiked(true);
                track.setCreatedAt(new Date());
                track.setUpdatedAt(new Date());
                track.setGenres(datas);

                tracks.add(track);

                TagEntity tagEntity = new TagEntity();
                tagEntity.setArtist(artist);
                tagEntity.setTracks(tracks);

                Response<TagEntity> response = Response.success(tagEntity);
                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<Response<String>> mockArtistProfileObservable() {

        return Observable.create(new Observable.OnSubscribe<Response<String>>() {

            @Override
            public void call(Subscriber<? super Response<String>> subscriber) {

                Response<String> response = Response.success("http://korcl.com/wp-content/uploads/2016/03/a_r_01.jpg");

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<Response<Artist>> mockArtistObservable() {

        return Observable.create(new Observable.OnSubscribe<Response<Artist>>() {

            @Override
            public void call(Subscriber<? super Response<Artist>> subscriber) {

                try {

                    List<Data> genres = new ArrayList<>();

                    Data tag = new Data();
                    tag.setName("Electro House");
                    tag.setType(Data.TYPE_GENRE);

                    Data tag1 = new Data();
                    tag1.setName("Folktronica");
                    tag1.setType(Data.TYPE_GENRE);

                    Data tag2 = new Data();
                    tag2.setName("House");
                    tag2.setType(Data.TYPE_GENRE);

                    genres.add(tag);
                    genres.add(tag1);
                    genres.add(tag2);

                    Artist artist = new Artist();

                    artist.setId(1);
                    artist.setName("Avicii");
                    artist.setLiked(true);
                    artist.setData(genres);

                    List<Feed> feeds = new ArrayList<>();

                    Feed feed;

                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+0000", Locale.getDefault());

                    feed = new Feed();

                    feed.setId("10154014752876799");
                    feed.setName("I think everyone had good feelings at the end of this era on my last show. \u2764\ufe0f");
                    feed.setPicture("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/14191993_10154014752876799_1374127911728930839_n.jpg?oh=dd6c6d410d3e20412b87438427570126&oe=5847561D");
                    feed.setCreatedAt(format.parse("2016-09-01T11:22:54+0000"));

                    feeds.add(feed);

                    feed = new Feed();
                    feed.setId("10154012727511799");
                    feed.setName("Sharing the last weekend experience with my siblings was all I could have wished for \u2764\ufe0f");
                    feed.setPicture("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/14100387_10154012727511799_7697410389555088857_n.jpg?oh=f537a3ac561b71a4c69144cf0b437368&oe=5854EB28");
                    feed.setCreatedAt(format.parse("2016-08-31T19:25:01+0000"));

                    feeds.add(feed);

                    feed = new Feed();
                    feed.setId("10154011796121799");
                    feed.setPicture("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/14184486_10154011796121799_1457308036939236315_n.jpg?oh=dd00709e2331b56db38e5e838e7c883e&oe=583A9C85");
                    feed.setCreatedAt(format.parse("2016-08-31T12:16:31+0000"));

                    feeds.add(feed);

                    feed = new Feed();
                    feed.setId("10154002565006799");
                    feed.setName("This sunday was my last show. But far from my last days in the world of music. Creating music is what makes me happy and I have gotten to know so many great people in my days of touring, seen so many amazing places and created endless of good memories. The decision I made might seem odd to some but everyone is different and for me this was the right one. Thank you to all my fans, my friends and family and everyone in the aviciicrew/family. This has been our journey together. \u2764\ufe0f");
                    feed.setPicture("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/14102522_10154002565006799_712609805402548964_n.jpg?oh=e21dbe77d1fa02bd6af39fa85ff33c20&oe=583C6705");
                    feed.setCreatedAt(format.parse("2016-08-31T12:00:27+0000"));

                    feeds.add(feed);

                    artist.setFeeds(feeds);

                    Response<Artist> response = Response.success(artist);

                    subscriber.onNext(response);
                    subscriber.onCompleted();
                }
                catch (ParseException e) {

                    subscriber.onError(e);
                }
            }
        });
    }

    public static Observable<Response<List<Track>>> mockTracksObservable() {

        return Observable.create(new Observable.OnSubscribe<Response<List<Track>>>() {

            @Override
            public void call(Subscriber<? super Response<List<Track>>> subscriber) {

                List<Data> genres;
                List<Track> tracks = new ArrayList<>();

                Track track;
                Data tag;

                // Waiting for Love

                genres = new ArrayList<>();

                tag = new Data();
                tag.setName("Electro House");
                tag.setType(Data.TYPE_GENRE);

                genres.add(tag);

                track  = new Track();

                track.setTitle("Waiting For Love");
                track.setLink("cHHLHGNpCSA");
                track.setLiked(true);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);

                // Wake Me Up

                genres = new ArrayList<>();

                tag = new Data();
                tag.setName("Electro House");
                tag.setType(Data.TYPE_GENRE);

                genres.add(tag);
/*
                tag = new Data();
                tag.setName("Folktronica");
                tag.setType(Data.TYPE_GENRE);

                genres.add(tag);
*/
                track  = new Track();

                track.setTitle("Wake Me Up");
                track.setLink("IcrbM1l_BoI");
                track.setLiked(false);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);

                // The Nights

                genres = new ArrayList<>();

                tag = new Data();
                tag.setName("Electro House");
                tag.setType(Data.TYPE_GENRE);

                genres.add(tag);

                track  = new Track();

                track.setTitle("The Nights");
                track.setLink("UtF6Jej8yb4");
                track.setLiked(false);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);


                // Hey Brother

                genres = new ArrayList<>();

                tag = new Data();
                tag.setName("Electro House");
                tag.setType(Data.TYPE_GENRE);

                genres.add(tag);

                track  = new Track();

                track.setTitle("Hey Brother");
                track.setLink("6Cp6mKbRTQY");
                track.setLiked(false);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);


                // For A Better Day

                genres = new ArrayList<>();

                tag = new Data();
                tag.setName("Electro House");
                tag.setType(Data.TYPE_GENRE);

                genres.add(tag);

                track  = new Track();

                track.setTitle("For A Better Day");
                track.setLink("Xq-knHXSKYY");
                track.setLiked(false);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);

                track  = new Track();

                track.setTitle("For A Better Day");
                track.setLink("Xq-knHXSKYY");
                track.setLiked(false);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);

                track  = new Track();

                track.setTitle("For A Better Day");
                track.setLink("Xq-knHXSKYY");
                track.setLiked(false);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);

                track  = new Track();

                track.setTitle("For A Better Day");
                track.setLink("Xq-knHXSKYY");
                track.setLiked(false);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);

                track  = new Track();

                track.setTitle("For A Better Day");
                track.setLink("Xq-knHXSKYY");
                track.setLiked(false);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);

                track  = new Track();

                track.setTitle("For A Better Day");
                track.setLink("Xq-knHXSKYY");
                track.setLiked(false);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);

                track  = new Track();

                track.setTitle("For A Better Day");
                track.setLink("Xq-knHXSKYY");
                track.setLiked(false);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);

                track  = new Track();

                track.setTitle("For A Better Day");
                track.setLink("Xq-knHXSKYY");
                track.setLiked(false);
                track.setCreatedAt(timestamp);
                track.setUpdatedAt(timestamp);
                track.setGenres(genres);

                tracks.add(track);

                Response<List<Track>> response = Response.success(tracks);

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    private static int fiestaId = 1;

    public static Observable<Response<List<Fiesta>>> mockFiesta() {

        return Observable.create(new Observable.OnSubscribe<Response<List<Fiesta>>>() {

            @Override
            public void call(Subscriber<? super Response<List<Fiesta>>> subscriber) {

                List<Fiesta> fiesta = new ArrayList<>();

                Fiesta item;

                for (int i = 0; i < 11; i++) {

                    item = new Fiesta();

                    item.setId(fiestaId++);

                    item.setName("UMF Korea 201" + i);
                    item.setDescription("orem ipsum dolor sit amet, consectetur adipiscing elit. Cras varius gravida tincidunt. Aliquam facilisis dolor sit amet fermentum maximus. Proin lobortis sem non nisl aliquet molestie. Mauris pellentesque egestas sem. Ut at risus sed orci molestie iaculis nec sed lacus. Maecenas erat sem, hendrerit id vehicula in, bibendum at diam.");
                    item.setStatus(Fiesta.STATUS_ATTEND);

                    item.setStartTime(timestamp);
                    item.setEndTime(timestamp);

                    item.setLocation("잠실 올림픽 주 경기장");
                    item.setLatitude(37.5148022);
                    item.setLongitude(127.0736261);

                    item.setCreatedAt(timestamp);
                    item.setUpdatedAt(timestamp);

                    fiesta.add(item);
                }

                Response<List<Fiesta>> response = Response.success(fiesta);

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<Response<Fiesta>> mockFiestaDetail() {

        return Observable.create(new Observable.OnSubscribe<Response<Fiesta>>() {

            @Override
            public void call(Subscriber<? super Response<Fiesta>> subscriber) {

                Artist artist;
                Perform perform;


                List<Data> data = new ArrayList<>();

                Data datum;

                datum = new Data();
                datum.setName("UltraKorea");
                datum.setType(Data.TYPE_GENRE);
                data.add(datum);

                datum = new Data();
                datum.setName("Electro House");
                datum.setType(Data.TYPE_GENRE);
                data.add(datum);

                datum = new Data();
                datum.setName("Progressive House");
                datum.setType(Data.TYPE_GENRE);
                data.add(datum);


                List<Perform> performs = new ArrayList<>();


                artist = new Artist();

                artist.setId(artistIdCount++);
                artist.setName("Avicii");

                perform = new Perform();

                perform.setId(performIdCount++);
                perform.setArtist(artist);
                perform.setStage("메인 스테이지");

                perform.setStartTime(timestamp);
                perform.setEndTime(timestamp);
                perform.setHeadliner(true);

                performs.add(perform);


                artist = new Artist();

                artist.setId(artistIdCount++);
                artist.setName("Knife Party");

                perform = new Perform();

                perform.setId(performIdCount++);
                perform.setArtist(artist);
                perform.setStage("메인 스테이지");

                perform.setStartTime(timestamp);
                perform.setEndTime(timestamp);
                perform.setHeadliner(true);

                performs.add(perform);


                artist = new Artist();

                artist.setId(artistIdCount++);
                artist.setName("Galantis");

                perform = new Perform();

                perform.setId(performIdCount++);
                perform.setArtist(artist);
                perform.setStage("라이브 스테이지");

                perform.setStartTime(timestamp);
                perform.setEndTime(timestamp);
                perform.setHeadliner(true);

                performs.add(perform);



                Fiesta fiesta = new Fiesta();

                fiesta.setName("UMF Korea 2016");
                fiesta.setDescription("드디어 완성된 울트라 코리아 2016의 풀 라인업!\n전세계 최정상급 아티스트들이 총출동 하는 아시아 최고의 뮤직 페스티벌, #울트라코리아2016 이제 시작하려 하고 있습니다!\n최고의 감동, 설렘과 흥분으로 아시아 대륙을 흔들어 놓을 준비되셨나요?");

                fiesta.setLatitude(37.5148022);
                fiesta.setLongitude(127.0736261);
                fiesta.setLocation("잠실 올림픽 주 경기장");

                fiesta.setStartTime(timestamp);
                fiesta.setEndTime(timestamp);

                fiesta.setCreatedAt(timestamp);
                fiesta.setUpdatedAt(timestamp);

                fiesta.setMeta(data);
                fiesta.setPerforms(performs);

                List<Photo> photos = new ArrayList<>();

                Photo photo;

                photo = new Photo();

                photo.setId(1L);
                photo.setLink("https://www.fest300.com/system/images/W1siZiIsIjIwMTQvMDQvMjAvMTYvMDUvMzYvMjQvVWx0cmFfMjAxNF9DYXNleV9GbGFuaWdhbl8yMy5qcGciXV0/Ultra_2014_Casey_Flanigan%20-%2023.jpg");

                photos.add(photo);

                photo = new Photo();

                photo.setId(2L);
                photo.setLink("https://theogsb.com/wp-content/uploads/2014/05/tomorrowland.jpg");

                photos.add(photo);

                photo = new Photo();

                photo.setId(3L);
                photo.setLink("http://www.trapstyle.com/wp-content/uploads/2016/02/edchalfway.jpg");

                photos.add(photo);

                fiesta.setPhotos(photos);

                Response<Fiesta> response = Response.success(fiesta);

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<Response<List<Fiesta>>> mockFiesta(long id, int count) {

        return mockFiesta();
    }

    public static Observable<Response<String>> mockPurchaseLink() {

        return Observable.create(new Observable.OnSubscribe<Response<String>>() {

            @Override
            public void call(Subscriber<? super Response<String>> subscriber) {

                Response<String> response = Response.success("http://ticket.yes24.com/Pages/Perf/Detail/Detail.aspx?IdPerf=24506&Gcode=009_109");

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    static Date timestamp = new Date();

    public static Observable<Response<List<Perform>>> mockPerformers() {

        return Observable.create(new Observable.OnSubscribe<Response<List<Perform>>>() {

            @Override
            public void call(Subscriber<? super Response<List<Perform>>> subscriber) {

                List<Perform> performs = new ArrayList<>();
                List<Artist> artists = artists();

                Perform perform;

                for (Artist artist : artists) {

                    perform = new Perform();

                    perform.setArtist(artist);
                    perform.setStage("메인 스테이지");

                    perform.setId(performIdCount++);
                    perform.setStartTime(timestamp);
                    perform.setEndTime(timestamp);
                    perform.setCreatedAt(timestamp);
                    perform.setUpdatedAt(timestamp);

                    performs.add(perform);
                }

                Response<List<Perform>> response = Response.success(performs);

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    static int performIdCount = 1;
    static int artistIdCount = 1;
    static int artistRankCount = 1;

    public static List<Artist> artists() {

        List<Artist> artists = new ArrayList<>();
        List<Data> tags = new ArrayList<>();

        Data data;

        data = new Data();
        data.setName("Electro House");
        data.setType(Data.TYPE_ARTIST);
        tags.add(data);
/*
        data = new Data();
        data.setName("Dance Pop");
        data.setType(Data.TYPE_ARTIST);
        tags.add(data);
*/

        Artist artist;

        artist = new Artist();
        artist.setId(artistIdCount++);
        artist.setName("Avicii");
        artist.setRank(artistRankCount++);
        artist.setData(tags);

        artists.add(artist);

        artist = new Artist();
        artist.setId(artistIdCount++);
        artist.setName("Knife Party");
        artist.setRank(artistRankCount++);
        artist.setData(tags);

        artists.add(artist);

        artist = new Artist();
        artist.setId(artistIdCount++);
        artist.setName("Galantis");
        artist.setRank(artistRankCount++);
        artist.setData(tags);

        artists.add(artist);

        artist = new Artist();
        artist.setId(artistIdCount++);
        artist.setName("Avicii");
        artist.setRank(artistRankCount++);
        artist.setData(tags);

        artists.add(artist);

        artist = new Artist();
        artist.setId(artistIdCount++);
        artist.setName("Knife Party");
        artist.setRank(artistRankCount++);
        artist.setData(tags);

        artists.add(artist);

        artist = new Artist();
        artist.setId(artistIdCount++);
        artist.setName("Galantis");
        artist.setRank(artistRankCount++);
        artist.setData(tags);

        artists.add(artist);

        artist = new Artist();
        artist.setId(artistIdCount++);
        artist.setName("Avicii");
        artist.setRank(artistRankCount++);
        artist.setData(tags);

        artists.add(artist);

        artist = new Artist();
        artist.setId(artistIdCount++);
        artist.setName("Knife Party");
        artist.setRank(artistRankCount++);
        artist.setData(tags);

        artists.add(artist);

        artist = new Artist();
        artist.setId(artistIdCount++);
        artist.setName("Galantis");
        artist.setRank(artistRankCount++);
        artist.setData(tags);

        artists.add(artist);

        return artists;
    }

    public static Observable<Response<List<Artist>>> mockArtistsObservable() {

        return Observable.create(new Observable.OnSubscribe<Response<List<Artist>>>() {

            @Override
            public void call(Subscriber<? super Response<List<Artist>>> subscriber) {

                List<Artist> artists = new ArrayList<>();

                artists.addAll(artists());
                artists.addAll(artists());

                Response<List<Artist>> response = Response.success(artists);

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<Response<Void>> mockNotificationRead(long id) {
        return Observable.create(new Observable.OnSubscribe<Response<Void>>() {

            @Override
            public void call(Subscriber<? super Response<Void>> subscriber) {

                Response<Void> response = Response.success(null);

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<Response<User>> me() {

        return Observable.create(new Observable.OnSubscribe<Response<User>>() {

            @Override
            public void call(Subscriber<? super Response<User>> subscriber) {

                List<Data> list = new ArrayList<>();

                Data data = new Data();
                data.setName("Electro House");
                data.setType(Data.TYPE_GENRE);
                list.add(data);

                Data data1 = new Data();
                data1.setName("Martin Garrix");
                data1.setType(Data.TYPE_ARTIST);
                list.add(data1);

                Data data2 = new Data();
                data2.setName("Don Diablo");
                data2.setType(Data.TYPE_ARTIST);
                list.add(data2);

                User user = new User();

                user.setId(1L);
                user.setBirth(new Date());
                user.setGender("Male");
                user.setName("Dongheon");
                user.setData(list);

                Response<User> response = Response.success(user);

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }

    public static Observable<Response<User>> register(String token, final String name, final Date birth, final String gender, MultipartBody.Part profile){

        return Observable.create(new Observable.OnSubscribe<Response<User>>(){
            @Override
            public void call(Subscriber<? super Response<User>> subscriber) {
                User user = new User();
                user.setBirth(birth);
                user.setName(name);
                user.setGender(gender);

                Response<User> response = Response.success(user);

                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });
    }
}
