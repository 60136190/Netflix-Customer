package com.example.moviettn.api;

import com.example.moviettn.model.request.CommentRequest;
import com.example.moviettn.model.request.RatingRequest;
import com.example.moviettn.model.response.CategoriesResponse;
import com.example.moviettn.model.response.CommentResponse;
import com.example.moviettn.model.response.DetailFilmResponse;
import com.example.moviettn.model.response.DirectorResponse;
import com.example.moviettn.model.response.FilmResponse;
import com.example.moviettn.model.response.ListFavoriteFilmResponse;
import com.example.moviettn.model.response.ResponseDTO;
import com.example.moviettn.model.response.SeriesFilmResponse;
import com.example.moviettn.model.test.ResponseTest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Film {

    // get all category
    @GET("api/category/all")
    Call<CategoriesResponse> getAllCategory(@Header("Authorization") String authorization);

    // get film follow category
    @GET("api/film/find/category/{id}")
    Call<FilmResponse> getFilmFollowCategory(@Header("Authorization") String authorization, @Path("id") String idCategory);

    // get all director
    @GET("api/director/all")
    Call<DirectorResponse> getAllDirector(@Header("Authorization") String authorization);

    // get film follow director
    @GET("api/film/find/director/{id}")
    Call<FilmResponse> getFilmFollowDirector(@Header("Authorization") String authorization, @Path("id") String idDirector);

    // get all film
    @GET("api/film/all")
    Call<FilmResponse> getAllFilm(@Header("Authorization") String authorization);

    // get all film /////////////////////////// TEST
    @GET("api/film/all")
    Call<ResponseTest> getAllFilmTest(@Header("Authorization") String authorization);

    // get film follow category
    @GET("api/film/kid")
    Call<FilmResponse> getFilmKid(@Header("Authorization") String authorization);

    // get detail film
    @GET("api/film/detail/{id}")
    Call<DetailFilmResponse> detailFilm(@Header("Authorization") String authorization,@Path("id") String idFilm);

    // get series film
    @GET("api/film/detail/{id}")
    Call<SeriesFilmResponse> getSeries(@Header("Authorization") String authorization, @Path("id") String idFilm);

    // add favorite film
    @POST("api/favourite/add/{id}")
    Call<ResponseDTO> addFavoriteFilm(@Header("Authorization") String authorization,@Path("id") String idFilm );

    // get list favorite film
    @GET("api/favourite/getList")
    Call<ListFavoriteFilmResponse> getListFavoriteFilm(@Header("Authorization") String authorization);

    // add rating film
    @POST("api/rating/add/{id}")
    Call<ResponseDTO> addRatingFilm(@Header("Authorization") String authorization, @Path("id") String idFilm, @Body RatingRequest ratingRequest);

    // add comment film
    @POST("api/comment/add/{id}")
    Call<ResponseDTO> addCommentFilm(@Header("Authorization") String authorization, @Path("id") String idFilm, @Body CommentRequest commentRequest);

    // get all comment film
    @GET("api/comment/get/{id}")
    Call<CommentResponse> getAllCommentFollowFilm(@Header("Authorization") String authorization, @Path("id") String idFilm);

    // update comment film
    @PATCH("api/comment/update/{id}")
    Call<ResponseDTO> editComment(@Header("Authorization") String authorization, @Path("id") String idComment, @Body CommentRequest commentRequest);

    // update comment film
    @DELETE("api/comment/delete/{id}")
    Call<ResponseDTO> deleteComment(@Header("Authorization") String authorization, @Path("id") String idComment);

}
