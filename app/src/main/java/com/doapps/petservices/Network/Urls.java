package com.doapps.petservices.Network;

/**
 * Created by RubyMobile-1 on 19/09/2016.
 */
interface Urls {
    String LOGIN = "users/login";
    String LOGIN_FB = "users/loginfb";
    String USER_SIGN_UP = "users/createUserClient";
    String UPDATE_USER = "users/updateUser";
    String CREATE_PET = "pets";
    String CREATE_POST = "publication_users/createPublication";
    String GET_USER_POST = "publication_users/getPublication";
    String LIKE = "publication_users/updateLike";
    String DISLIKE = "publication_users/DisLike";
    String FILTER_POST = "publication_users/getPublicationType";
}
