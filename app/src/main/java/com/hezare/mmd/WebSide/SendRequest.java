package com.hezare.mmd.WebSide;

import android.util.Log;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.hezare.mmd.App;

import java.io.File;
import java.io.IOException;

import okhttp3.Response;

/**
 * Created by amirhododi on 7/29/2017.
 */


public class SendRequest {
    public static OnLoginCompleteListner onCardLoginCompleteClickListner;
    public static OnLoginErrorListner onCardLoginErrorClickListner;
    public static OnBarnameHaftegiCompleteListner onCardBarnameHaftegiCompleteClickListner;
    public static OnBarnameHaftegiErrorListner onCardBarnameHaftegiErrorClickListner;
    public static OnListStudentsCompleteListner onCardListStudentsCompleteClickListner;
    public static OnListStudentsErrorListner onCardListStudentsErrorClickListner;
    public static OnListClassNumberCompleteListner onCardListClassNumberCompleteClickListner;
    public static OnListClassNumberErrorListner onCardListClassNumberErrorClickListner;
    public static OnAbsentCompleteListner onCardAbsentCompleteClickListner;
    public static OnAbsentErrorListner onCardAbsentErrorClickListner;
    public static OnHomeWorkCompleteListner onCardHomeWorkCompleteClickListner;
    public static OnHomeWorkErrorListner onCardHomeWorkErrorClickListner;
    public static OnExamGradeCompleteListner onCardExamGradeCompleteClickListner;
    public static OnExamGradeErrorListner onCardExamGradeErrorClickListner;
    public static OnForgetCompleteListner onCardForgetCompleteClickListner;
    public static OnForgetErrorListner onCardForgetErrorClickListner;
    public static OnLogOutCompleteListner onCardLogOutCompleteClickListner;
    public static OnLogOutErrorListner onCardLogOutErrorClickListner;
    public static OnChangePassCompleteListner onCardChangePassCompleteClickListner;
    public static OnChangePassErrorListner onCardChangePassErrorClickListner;
    public static OnSendChatCompleteListner onCardSendChatCompleteClickListner;
    public static OnSendChatErrorListner onCardSendChatErrorClickListner;
    public static OnReadChatCompleteListner onCardReadChatCompleteClickListner;
    public static OnReadChatErrorListner onCardReadChatErrorClickListner;
    public static OnTashvighoTanbiheKelasiCompleteListner onCardTashvighoTanbiheKelasiCompleteClickListner;
    public static OnTashvighoTanbiheKelasiErrorListner onCardTashvighoTanbiheKelasiErrorClickListner;

    public static void SendPostLogin(String UserName, String Password, String AndroidId) {
        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidAccount/Login?UserName=" + UserName + "&Password=" + Password + "&AndroidId=" + AndroidId + "&Type=Moallem");
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                App.setCookie(response);
                                try {
                                    onCardLoginCompleteClickListner.OnLoginCompleteed(response.body().source().readUtf8());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardLoginErrorClickListner.OnLoginErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void SendPostBarnameHaftegi(String MoallemID) {
        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/BarnameHaftegi?MoallemId=" + MoallemID);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);

        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {

                                    //   Log.e("Login", "response : " + response.body().source().readUtf8());
                                    onCardBarnameHaftegiCompleteClickListner.OnBarnameHaftegiCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardBarnameHaftegiErrorClickListner.OnBarnameHaftegiErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void SendPostListStudents(String ClassID) {
        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/ListDaneshAmoozan?KelasId=" + ClassID);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);

        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {

                                    //   Log.e("Login", "response : " + response.body().source().readUtf8());
                                    onCardListStudentsCompleteClickListner.OnListStudentsCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardListStudentsErrorClickListner.OnListStudentsErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void SendPostClassNumber(String DaneshAmoozId, String BarnameHaftegiId, String Date, int Week, String Nomre, String Tozih) {


        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/SabteNomreKelasi?DaneshAmoozId=" + DaneshAmoozId + "&BarnameHaftegiId=" + BarnameHaftegiId + "&Tarikh=" + Date + "&Hafte=" + Week + "&Nomre=" + Nomre + "&Tozih=" + Tozih);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardListClassNumberCompleteClickListner.OnListClassNumberCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardListClassNumberErrorClickListner.OnListClassNumberErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void SendPostForget(String Tell) {


        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidAccount/ForgottenPassword?Tell=" + Tell + "&Type=Moallem");
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardForgetCompleteClickListner.OnForgetCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardForgetErrorClickListner.OnForgetErrored(anError.getErrorDetail());
                    }
                });


    }

    public static void SendPostLogOut() {


        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidAccount/LogOut");
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardLogOutCompleteClickListner.OnLogOutCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardLogOutErrorClickListner.OnLogOutErrored(anError.getErrorDetail());
                    }
                });


    }

    public static void SendPostChangePass(String OldPassword, String NewPassword) {


        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidAccount/ChangePassword?OldPassword=" + OldPassword + "&NewPassword=" + NewPassword);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardChangePassCompleteClickListner.OnChangePassCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardChangePassErrorClickListner.OnChangePassErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void SendPostSendChat(String Text, String From_Id, String To_Id) {


        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/ErsalePayamBe?Text=" + Text + "&From_Id=" + From_Id + "&To_Id=" + To_Id);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardSendChatCompleteClickListner.OnSendChatCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardSendChatErrorClickListner.OnSendChatErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void SendPostReadChat(String From_Id, String To_Id, int page) {
        Log.e("List", "http://soldaschool.ir/api/AndroidService/DaryafteChat?From_Id=" + From_Id + "&To_Id=" + To_Id + "&page=" + page);

        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/DaryafteChat?From_Id=" + From_Id + "&To_Id=" + To_Id + "&page=" + page);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardReadChatCompleteClickListner.OnReadChatCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardReadChatErrorClickListner.OnReadChatErrored(anError.getErrorDetail());
                    }
                });


    }

    public static void SendPostExamNumber(String DaneshAmoozId, String BarnameHaftegiId, String Nomre, String Tozih) {


        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/SabteNomreEmtehani?DaneshAmoozId=" + DaneshAmoozId + "&BarnameHaftegiId=" + BarnameHaftegiId + "&Nomre=" + Nomre + "&Tozih=" + Tozih);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardExamGradeCompleteClickListner.OnExamGradeCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardExamGradeErrorClickListner.OnExamGradeErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void SendPostAbsent(String DaneshAmoozId, String BarnameHaftegiId, String Takhir, String Tozih, int Hafte, String Tarikh) {

        Log.e("Sabt", "http://soldaschool.ir/api/AndroidService/HuzurGhiabeKelasi?DaneshAmoozId=" + DaneshAmoozId + "&BarnameHaftegiId=" + BarnameHaftegiId + "&Takhir=" + Takhir + "&Tozih=" + Tozih + "&Hafte=" + Hafte + "&Tarikh=" + Tarikh);

        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/HuzurGhiabeKelasi?DaneshAmoozId=" + DaneshAmoozId + "&BarnameHaftegiId=" + BarnameHaftegiId + "&Takhir=" + Takhir + "&Tozih=" + Tozih + "&Hafte=" + Hafte + "&Tarikh=" + Tarikh);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardAbsentCompleteClickListner.OnAbsentCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardAbsentErrorClickListner.OnAbsentErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void SendPostHomeWork(String DaneshAmoozId, String BarnameHaftegiId, String Date, int Hafte, String OnvaneFile, String TozihateFile, String File, String Type) {

        Log.e("Sabt", "http://soldaschool.ir/Home/TaklifeKelasi?DaneshAmoozId=" + DaneshAmoozId + "&BarnameHaftegiId=" + BarnameHaftegiId + "&Date=" + Date + "&Hafte=" + Hafte + "&OnvaneFile=" + OnvaneFile + "&TozihateFile=" + TozihateFile + "&File=" + File);
        ANRequest.MultiPartBuilder a = new ANRequest.MultiPartBuilder("http://soldaschool.ir/Home/TaklifeKelasi");
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookieFile(a);
        a.addMultipartParameter("DaneshAmoozId", DaneshAmoozId);
        a.addMultipartParameter("BarnameHaftegiId", BarnameHaftegiId);
        a.addMultipartParameter("Date", Date);
        a.addMultipartParameter("Hafte", Hafte + "");
        a.addMultipartParameter("OnvaneFile", OnvaneFile);
        a.addMultipartParameter("TozihateFile", TozihateFile);
        a.addMultipartFile("File", new File(File));
        a.addMultipartParameter("Type", Type);
        a.build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        Log.e("Image", " timeTakenInMillis : " + timeTakenInMillis);
                        Log.e("Image", " bytesSent : " + bytesSent);
                        Log.e("Image", " bytesReceived : " + bytesReceived);
                        Log.e("Image", " isFromCache : " + isFromCache);
                    }
                })

                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardHomeWorkCompleteClickListner.OnHomeWorkCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardHomeWorkErrorClickListner.OnHomeWorkErrored(anError.getErrorDetail());
                    }
                });


    }


    public static void SendPostTashvighoTanbiheKelasi(String DaneshAmoozId, String BarnameHaftegiId, String Emtiaz, String Tozihat, String Hafte, String Tarikh) {
        final ANRequest.PostRequestBuilder a = new ANRequest.PostRequestBuilder("http://soldaschool.ir/api/AndroidService/TashvighoTanbiheKelasi?DaneshAmoozId=" + DaneshAmoozId + "&BarnameHaftegiId=" + BarnameHaftegiId + "&Emtiaz=" + Emtiaz + "&Tozihat=" + Tozihat + "&Hafte=" + Hafte + "&Tarikh=" + Tarikh);
        a.setPriority(Priority.HIGH);
        a.doNotCacheResponse();
        App.getCookie(a);
        a.build()


                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e("Login", "response is successful");
                                try {
                                    onCardTashvighoTanbiheKelasiCompleteClickListner.OnTashvighoTanbiheKelasiCompleteed(response.body().source().readUtf8());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e("Login", "response is not successful : " + response.code());
                            }
                        } else {
                            Log.e("Login", "response is null");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Login", anError.getErrorDetail());
                        onCardTashvighoTanbiheKelasiErrorClickListner.OnTashvighoTanbiheKelasiErrored(anError.getErrorDetail());
                    }
                });


    }

    public void setOnLoginCompleteListner(OnLoginCompleteListner onCardLoginCompleteClickListner) {
        this.onCardLoginCompleteClickListner = onCardLoginCompleteClickListner;
    }

    public void setOnLoginErrorListner(OnLoginErrorListner onCardLoginErrorCompleteClickListner) {
        this.onCardLoginErrorClickListner = onCardLoginErrorCompleteClickListner;
    }

    public void setOnBarnameHaftegiCompleteListner(OnBarnameHaftegiCompleteListner onCardBarnameHaftegiCompleteClickListner) {
        this.onCardBarnameHaftegiCompleteClickListner = onCardBarnameHaftegiCompleteClickListner;
    }

    public void setOnBarnameHaftegiErrorListner(OnBarnameHaftegiErrorListner onCardBarnameHaftegiErrorCompleteClickListner) {
        this.onCardBarnameHaftegiErrorClickListner = onCardBarnameHaftegiErrorCompleteClickListner;
    }

    public void setOnListStudentsCompleteListner(OnListStudentsCompleteListner onCardListStudentsCompleteClickListner) {
        this.onCardListStudentsCompleteClickListner = onCardListStudentsCompleteClickListner;
    }

    public void setOnListStudentsErrorListner(OnListStudentsErrorListner onCardListStudentsErrorCompleteClickListner) {
        this.onCardListStudentsErrorClickListner = onCardListStudentsErrorCompleteClickListner;
    }

    public void setOnListClassNumberCompleteListner(OnListClassNumberCompleteListner onCardListClassNumberCompleteClickListner) {
        this.onCardListClassNumberCompleteClickListner = onCardListClassNumberCompleteClickListner;
    }

    public void setOnListClassNumberErrorListner(OnListClassNumberErrorListner onCardListClassNumberErrorCompleteClickListner) {
        this.onCardListClassNumberErrorClickListner = onCardListClassNumberErrorCompleteClickListner;
    }

    public void setOnAbsentCompleteListner(OnAbsentCompleteListner onCardAbsentCompleteClickListner) {
        this.onCardAbsentCompleteClickListner = onCardAbsentCompleteClickListner;
    }

    public void setOnAbsentErrorListner(OnAbsentErrorListner onCardAbsentErrorCompleteClickListner) {
        this.onCardAbsentErrorClickListner = onCardAbsentErrorCompleteClickListner;
    }

    public void setOnHomeWorkCompleteListner(OnHomeWorkCompleteListner onCardHomeWorkCompleteClickListner) {
        this.onCardHomeWorkCompleteClickListner = onCardHomeWorkCompleteClickListner;
    }

    public void setOnHomeWorkErrorListner(OnHomeWorkErrorListner onCardHomeWorkErrorCompleteClickListner) {
        this.onCardHomeWorkErrorClickListner = onCardHomeWorkErrorCompleteClickListner;
    }

    public void setOnExamGradeCompleteListner(OnExamGradeCompleteListner onCardExamGradeCompleteClickListner) {
        this.onCardExamGradeCompleteClickListner = onCardExamGradeCompleteClickListner;
    }

    public void setOnExamGradeErrorListner(OnExamGradeErrorListner onCardExamGradeErrorCompleteClickListner) {
        this.onCardExamGradeErrorClickListner = onCardExamGradeErrorCompleteClickListner;
    }

    public void setOnForgetCompleteListner(OnForgetCompleteListner onCardForgetCompleteClickListner) {
        this.onCardForgetCompleteClickListner = onCardForgetCompleteClickListner;
    }

    public void setOnForgetErrorListner(OnForgetErrorListner onCardForgetErrorCompleteClickListner) {
        this.onCardForgetErrorClickListner = onCardForgetErrorCompleteClickListner;
    }

    public void setOnLogOutCompleteListner(OnLogOutCompleteListner onCardLogOutCompleteClickListner) {
        this.onCardLogOutCompleteClickListner = onCardLogOutCompleteClickListner;
    }

    public void setOnLogOutErrorListner(OnLogOutErrorListner onCardLogOutErrorCompleteClickListner) {
        this.onCardLogOutErrorClickListner = onCardLogOutErrorCompleteClickListner;
    }

    public void setOnChangePassCompleteListner(OnChangePassCompleteListner onCardChangePassCompleteClickListner) {
        this.onCardChangePassCompleteClickListner = onCardChangePassCompleteClickListner;
    }

    public void setOnChangePassErrorListner(OnChangePassErrorListner onCardChangePassErrorCompleteClickListner) {
        this.onCardChangePassErrorClickListner = onCardChangePassErrorCompleteClickListner;
    }

    public void setOnSendChatCompleteListner(OnSendChatCompleteListner onCardSendChatCompleteClickListner) {
        this.onCardSendChatCompleteClickListner = onCardSendChatCompleteClickListner;
    }

    public void setOnSendChatErrorListner(OnSendChatErrorListner onCardSendChatErrorCompleteClickListner) {
        this.onCardSendChatErrorClickListner = onCardSendChatErrorCompleteClickListner;
    }

    public void setOnReadChatCompleteListner(OnReadChatCompleteListner onCardReadChatCompleteClickListner) {
        this.onCardReadChatCompleteClickListner = onCardReadChatCompleteClickListner;
    }

    public void setOnReadChatErrorListner(OnReadChatErrorListner onCardReadChatErrorCompleteClickListner) {
        this.onCardReadChatErrorClickListner = onCardReadChatErrorCompleteClickListner;
    }

    public void setOnTashvighoTanbiheKelasiCompleteListner(OnTashvighoTanbiheKelasiCompleteListner onCardTashvighoTanbiheKelasiCompleteClickListner) {
        this.onCardTashvighoTanbiheKelasiCompleteClickListner = onCardTashvighoTanbiheKelasiCompleteClickListner;
    }

    public void setOnTashvighoTanbiheKelasiErrorListner(OnTashvighoTanbiheKelasiErrorListner onCardTashvighoTanbiheKelasiErrorCompleteClickListner) {
        this.onCardTashvighoTanbiheKelasiErrorClickListner = onCardTashvighoTanbiheKelasiErrorCompleteClickListner;
    }

    public interface OnLoginCompleteListner {
        void OnLoginCompleteed(String response);
    }

    public interface OnLoginErrorListner {
        void OnLoginErrored(String response);
    }

    public interface OnBarnameHaftegiCompleteListner {
        void OnBarnameHaftegiCompleteed(String response);
    }

    public interface OnBarnameHaftegiErrorListner {
        void OnBarnameHaftegiErrored(String response);
    }

    public interface OnListStudentsCompleteListner {
        void OnListStudentsCompleteed(String response);
    }

    public interface OnListStudentsErrorListner {
        void OnListStudentsErrored(String response);
    }

    public interface OnListClassNumberCompleteListner {
        void OnListClassNumberCompleteed(String response);
    }

    public interface OnListClassNumberErrorListner {
        void OnListClassNumberErrored(String response);
    }

    public interface OnAbsentCompleteListner {
        void OnAbsentCompleteed(String response);
    }

    public interface OnAbsentErrorListner {
        void OnAbsentErrored(String response);
    }

    public interface OnHomeWorkCompleteListner {
        void OnHomeWorkCompleteed(String response);
    }

    public interface OnHomeWorkErrorListner {
        void OnHomeWorkErrored(String response);
    }

    public interface OnExamGradeCompleteListner {
        void OnExamGradeCompleteed(String response);
    }

    public interface OnExamGradeErrorListner {
        void OnExamGradeErrored(String response);
    }

    public interface OnForgetCompleteListner {
        void OnForgetCompleteed(String response);
    }

    public interface OnForgetErrorListner {
        void OnForgetErrored(String response);
    }

    public interface OnLogOutCompleteListner {
        void OnLogOutCompleteed(String response);
    }

    public interface OnLogOutErrorListner {
        void OnLogOutErrored(String response);
    }

    public interface OnChangePassCompleteListner {
        void OnChangePassCompleteed(String response);
    }

    public interface OnChangePassErrorListner {
        void OnChangePassErrored(String response);
    }

    public interface OnSendChatCompleteListner {
        void OnSendChatCompleteed(String response);
    }

    public interface OnSendChatErrorListner {
        void OnSendChatErrored(String response);
    }

    public interface OnReadChatCompleteListner {
        void OnReadChatCompleteed(String response);
    }

    public interface OnReadChatErrorListner {
        void OnReadChatErrored(String response);
    }

    public interface OnTashvighoTanbiheKelasiCompleteListner {
        void OnTashvighoTanbiheKelasiCompleteed(String response);
    }

    public interface OnTashvighoTanbiheKelasiErrorListner {
        void OnTashvighoTanbiheKelasiErrored(String response);
    }
}
