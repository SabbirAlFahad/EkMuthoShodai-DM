package com.itbl.ekmuthoshodai;
import com.itbl.ekmuthoshodai.entities.NewProductData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NewProductInt{

    @POST(" ")
    Call<NewProductData> PostData(@Body NewProductData NewProductData);

}
