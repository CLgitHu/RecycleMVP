package application.android.com.mymvp.model.impl;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import application.android.com.mymvp.bean.Data;
import application.android.com.mymvp.model.IDataModel;
import application.android.com.mymvp.presenter.IDataListener;
import application.android.com.mymvp.utils.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DataModelImpl implements IDataModel {

    private static final String URL = "http://www.wanandroid.com/banner/json";

    public DataModelImpl(Context context) {

    }

    @Override
    public void loadData(final IDataListener listener) {
        HttpUtil.getInstance().sendOkHttpRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailed();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();

                //先转JsonObject
                JsonObject jsonObject = new JsonParser().parse(responseData).getAsJsonObject();
                //再转JsonArray 加上数据头
                JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                Gson gson = new Gson();
                ArrayList<Data> dataArrayList = new ArrayList<>();

                //循环遍历
                for (JsonElement user : jsonArray) {
                    //通过反射 得到UserBean.class
                    Data data = gson.fromJson(user, new TypeToken<Data>() {}.getType());
                    dataArrayList.add(data);
                }
//                Gson gson = new Gson();
//                Data data = gson.fromJson(responseData, Data.class);
                listener.onSuccess(dataArrayList);
            }
        });
    }

}
