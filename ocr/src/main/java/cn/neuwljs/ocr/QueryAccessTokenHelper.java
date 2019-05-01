package cn.neuwljs.ocr;

import android.content.Context;

import cn.neuwljs.network.Network;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static cn.neuwljs.ocr.Constant.GRANT_TYPE;
import static cn.neuwljs.ocr.Constant.OCR_AK;
import static cn.neuwljs.ocr.Constant.OCR_SK;
import static cn.neuwljs.ocr.Constant.OCR_XML_ACCESS_TOKEN_KEY;
import static cn.neuwljs.ocr.Constant.OCR_XML_ACCESS_TOKEN_TIME_KEY;

/**
 * 拿到access_token的实现类
 */
class QueryAccessTokenHelper extends QueryDataHelper<AccessToken> {

    // access_token的有效期
    private static final long TERM_OF_VALIDITY = 29L*24L*60L*60L*1000L;

    private Context mContext;

    QueryAccessTokenHelper(Context context) {
        mContext = context;
    }

    @Override
    public Observable<AccessToken> queryFromNet() {
        return Network.query ()
                .create (Api.class)
                .queryOCRAccessToken (GRANT_TYPE, OCR_AK, OCR_SK)
                .filter (accessToken ->
                        accessToken != null && accessToken.getAccessToken () != null)
                .flatMap ((Function<AccessToken, ObservableSource<AccessToken>>) accessToken -> {

                    // 写入sharePreference
                    XmlIOUtil.xmlPut (OCR_XML_ACCESS_TOKEN_KEY, accessToken.getAccessToken (), mContext);
                    XmlIOUtil.xmlPut (OCR_XML_ACCESS_TOKEN_TIME_KEY, System.currentTimeMillis (), mContext);

                    return Observable.create (emitter -> {
                        emitter.onNext (accessToken);
                        emitter.onComplete ();
                    });
                });
    }

    @Override
    public Observable<AccessToken> queryFromLocal() {

        // 从sharePreference拿数据
        long time = XmlIOUtil.xmlGetLong (OCR_XML_ACCESS_TOKEN_TIME_KEY, mContext);

        if(time != 0){

            // 过期重新获取
            if(System.currentTimeMillis () - time >= TERM_OF_VALIDITY){
                return queryFromNet();
            }else{
                return Observable.create (emitter -> {
                    AccessToken accessToken;

                    // 从sharePreference拿数据
                    String accessTokenValue = XmlIOUtil.xmlGetString (OCR_XML_ACCESS_TOKEN_KEY, mContext);
                    if(accessTokenValue != null){
                        accessToken = new AccessToken ();
                        accessToken.setAccessToken (accessTokenValue);
                        emitter.onNext (accessToken);
                    }
                    emitter.onComplete ();
                });
            }
        }else{
//            return Observable.create (new ObservableOnSubscribe<AccessToken> () {
//                @Override
//                public void subscribe(ObservableEmitter<AccessToken> emitter) throws Exception {
//                    emitter.onComplete ();
//                }
//            });
            return queryFromNet();
        }
    }
}
