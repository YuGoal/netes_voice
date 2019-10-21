package io.yugoal.lib_audio.mediaplayer.db;

import android.database.sqlite.SQLiteDatabase;

import io.yugoal.lib_audio.app.AudioHelper;
import io.yugoal.lib_audio.mediaplayer.model.AudioBean;
import io.yugoal.lib_audio.mediaplayer.model.Favourite;

/**
 * @author caoyu
 * date  2019/10/16
 * 操作数据库帮助类
 */
public class GreenDaoHelper {
    private static final String DB_BAME = "music_db";

    private static DaoMaster.DevOpenHelper helper;
    private static SQLiteDatabase mDb;
    //管理数据库
    private static DaoMaster mDaoMaster;
    //管理各种实体Dao,不让业务层拿到session直接去操作数据库，统一由此类提供方法
    private static DaoSession mDaoSession;

    public static void initDatabase() {
        helper = new DaoMaster.DevOpenHelper(AudioHelper.getContext(), DB_BAME);
        mDb = helper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDb);
        mDaoSession = mDaoMaster.newSession();
    }

    /**
     * 添加感兴趣
     */
    public static void addFavourite(AudioBean audioBean) {
        FavouriteDao dao = mDaoSession.getFavouriteDao();
        Favourite favourite = new Favourite();
        favourite.setAudioId(audioBean.id);
        favourite.setAudioBean(audioBean);
        dao.insert(favourite);
    }

    /**
     * 移除感兴趣
     */
    public static void removeFavourite(AudioBean audioBean) {
        FavouriteDao dao = mDaoSession.getFavouriteDao();
        Favourite favourite = dao.queryBuilder().where(FavouriteDao.Properties.AudioId.eq(audioBean.id)).unique();
        dao.delete(favourite);
    }

    /**
     * 查找感兴趣
     */
    public static Favourite selectFavourite(AudioBean audioBean) {
        FavouriteDao dao = mDaoSession.getFavouriteDao();
        Favourite favourite = dao.queryBuilder().where(FavouriteDao.Properties.AudioId.eq(audioBean.id)).unique();
        return favourite;
    }
}
