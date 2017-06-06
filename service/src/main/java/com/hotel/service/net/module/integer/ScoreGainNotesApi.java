package com.hotel.service.net.module.integer;

import com.hotel.service.net.BaseApi;
import com.hotel.service.net.ViewProxyInterface;
import com.hotel.service.net.module.integer.model.ReqScoreGainNotes;
import com.hotel.service.net.module.integer.model.ResScoreGainNotes;
import com.hotel.service.net.module.market.model.ReqMenuList;

import javax.inject.Inject;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by hyz on 2015/12/15.
 */
public class ScoreGainNotesApi extends BaseApi {

    static ScoreGainNotesInterface server;

    @Inject
    public ScoreGainNotesApi (RestAdapter restAdapter){
        server = restAdapter.create(ScoreGainNotesInterface.class);
    }

    public Observable<ResScoreGainNotes> getScoreGainNotesList(ReqScoreGainNotes reqScoreGainNotes) {
        return getScoreGainNotesList(reqScoreGainNotes, null);
    }

    public Observable<ResScoreGainNotes> getScoreGainNotesList(ReqScoreGainNotes reqScoreGainNotes, ViewProxyInterface viewProxy) {
        onLoading(viewProxy);
        reqScoreGainNotes.module = ReqMenuList.MODULE_BUY;
        reqScoreGainNotes.method = ReqMenuList.METHOD_GET_SCORE_LIST;

        return getRes(server.getScoreGainNotesList(reqScoreGainNotes), viewProxy);
    }

}
