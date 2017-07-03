package com.doapps.petservices.Network;

/**
 * Created by RubyMobile-1 on 19/09/2016.
 */
interface Urls {
    String USER_DATA = "users/{id}";
    String LOGIN = "users/login";
    String LOGIN_FB = "users/loginfb";
    String USER_SIGN_UP = "users/createUserClient";
    String EMPRESA_SIGN_UP = "users/createUserClientEnterprise";
    String UPDATE_USER = "users/updateUser";
    String CREATE_PET = "pets/image";
    String CREATE_POST = "publication_users/createPublication";
    String GET_ALL_POST= "publication_users";
    String GET_USER_POST = "publication_users/getPublication";
    String LIKE = "publication_users/updateLike";
    String DISLIKE = "publication_users/DisLike";
    String FILTER_POST = "publication_users/getPublicationType";
    String MASCOTAS = "pets/listPetUser";
}