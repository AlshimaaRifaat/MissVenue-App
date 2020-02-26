package com.ibsvalleyn.missvenue.api;

import com.ibsvalleyn.missvenue.models.Add_to_cart;
import com.ibsvalleyn.missvenue.models.Banners;
import com.ibsvalleyn.missvenue.models.City_model;
import com.ibsvalleyn.missvenue.models.CustomerProfile;
import com.ibsvalleyn.missvenue.models.Guest_session;
import com.ibsvalleyn.missvenue.models.HomeModel;
import com.ibsvalleyn.missvenue.models.LoginResponse;
import com.ibsvalleyn.missvenue.models.OrderCheck;
import com.ibsvalleyn.missvenue.models.OrderView.OrderView;
import com.ibsvalleyn.missvenue.models.Orders;
import com.ibsvalleyn.missvenue.models.OrdersModel;
import com.ibsvalleyn.missvenue.models.Password_fullrecovery_model;
import com.ibsvalleyn.missvenue.models.Password_recovery_model;
import com.ibsvalleyn.missvenue.models.ProductDetails;
import com.ibsvalleyn.missvenue.models.Product_Categories;
import com.ibsvalleyn.missvenue.models.Products;
import com.ibsvalleyn.missvenue.models.RegisterResponse;
import com.ibsvalleyn.missvenue.models.RelatedProducts;
import com.ibsvalleyn.missvenue.models.ReviewModel;
import com.ibsvalleyn.missvenue.models.SearchProductCategory;
import com.ibsvalleyn.missvenue.models.Sectors;
import com.ibsvalleyn.missvenue.models.ShoppingCarts;
import com.ibsvalleyn.missvenue.models.SplashModel;
import com.ibsvalleyn.missvenue.models.StaticsModel;
import com.ibsvalleyn.missvenue.models.User_address_model;
import com.ibsvalleyn.missvenue.models.Wishlists;
import com.ibsvalleyn.missvenue.models.address_add_model;
import com.ibsvalleyn.missvenue.models.Country_Model;
import com.ibsvalleyn.missvenue.models.customer_update_model;
import com.ibsvalleyn.missvenue.models.search.FilterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Api {

    @FormUrlEncoded
    @POST("guest/Register")
    Call<RegisterResponse> userRegister(@Field("customerid") int customerid,
                                        @Field("firstname") String firstName,
                                        @Field("lastname") String lastName,
                                        @Field("email") String email,
                                        @Field("phone") String phone_No,
                                        @Field("password") String password);

    @FormUrlEncoded
    @POST("customer/login")
    Call<LoginResponse> userLogin(@Field("Email") String email,
                                  @Field("Password") String password);

    @FormUrlEncoded
    @POST("order/add")
    Call<customer_update_model> api_order_add(@Field("CustomerId") int CustomerId,
                                              @Field("shippingId") Integer shippingId
            , @Field("billingId") Integer billingId,
                                              @Field("paymentmethodname") String paymentmethodname
            , @Field("couponCode") String couponCode
            , @Field("customerIP") String customerIP
            , @Field("totalAmount") double totalAmount,
                                              @Field("subTotal") float subTotal,
                                              @Field("TaxAmount") float TaxAmount,
                                              @Field("shippingAmount") float shippingAmount);

    @GET("customer/address")
    Call<List<User_address_model>> getAddressById(@Query("Customer_Id") int Customer_Id,
                                                  @Query("Address_Id") int Address_Id);

    @FormUrlEncoded
    @POST("password/fullrecovery")
    Call<Password_fullrecovery_model> PASSWORD_FULLRECOVERY_MODEL_CALL(@Field("Code") String Code,
                                                                       @Field("email") String email, @Field("Password") String Password);

    @FormUrlEncoded
    @POST("password/recovery")
    Call<Password_recovery_model> PASSWORD_RECOVERY_MODEL_CALL(@Field("email") String email);

    @GET("Category/Sectors")
    Call<List<Sectors>> getSectors(@Query("count") int count);


    @GET("Category/home?CustomerId=")
    Call<HomeModel> getCategories(@Query("count") int count,
                                  @Query("sector_id") int sector_id, @Query("PageNumber") int PageNumber, @Query("RowCountPerPage") int RowCountPerPage);

    @GET("Category/details?subcategoryfilter=null&brandfilter=null&minprice=null&maxprice=null&attributefilter=null&customer_id=")
    Call<Product_Categories> getCategoriesProduct(@Query("category_id") int category_id, @Query("PageNumber") int PageNumber, @Query("RowCountPerPage") int RowCountPerPage);

    @GET("Category/RefineSearch")
    Call<FilterModel> getFiletr(@Query("categoryId") int category_id);

    @GET("Search/RefineSearch")
    Call<FilterModel> getFiletrRefineSearch(@Query("searchword") String searchword);

    @FormUrlEncoded
    @POST("Category/Filter?customerId=")
    Call<List<Products>> PRODUCTS_CALL_Filter(@Field("category_id") int category_id,
                                              @Field("subcategoryfilter") String subcategoryfilter,
                                              @Field("brandfilter") String brandfilter,
                                              @Field("minprice") double minprice,
                                              @Field("maxprice") double maxprice,
                                              @Field("attributefilter") String attributefilter);

    @FormUrlEncoded
    @POST("Search/Filter?customerId=")
    Call<List<SearchProductCategory>> PRODUCTS_CALL_FilterSearchfilteResult(@Field("searchword") String searchword,
                                                                            @Field("subcategoryfilter") String subcategoryfilter,
                                                                            @Field("brandfilter") String brandfilter,
                                                                            @Field("minprice") double minprice,
                                                                            @Field("maxprice") double maxprice,
                                                                            @Field("attributefilter") String attributefilter);

    @GET("Category/Filter?customerId=2773&category_id=17&subcategoryfilter=null&brandfilter=null&minprice=0&maxprice=10000&attributefilter=null")
    Call<List<Products>> PRODUCTS_CALL_Filter(/*@Query("customer_id") int customer_id, @Query("category_id") int category_id,
                                 @Query("subcategoryfilter") String subcategoryfilter,
                                 @Query("brandfilter") String brandfilter,
                                 @Query("minprice") float minprice,
                                 @Query("maxprice") float maxprice,
                                 @Query("attributefilter") String attributefilter*/);

    @GET("order/view")
    Call<OrderView> ORDER_VIEW_CALL();

    @GET("customer/details")
    Call<CustomerProfile> userProfile(@Query("id") int id);

    @GET("Customer/Orderlist")
    Call<List<Orders>> getOrders(@Query("customerId") int id);

    @GET("Shoppingcart/details")
    Call<ShoppingCarts> getCart(@Query("customerId") int id);

    @GET("wishlist/details")
    Call<Wishlists> getWishlist(@Query("customerId") int id);

    //https://api2.missvenue.com/ar-SA/api/product/select?customerId=22629&categoryId=null&manufacturerId=null&productId=320&Hasoffer=null&PageNumber=null&RowCountPerPage=null
    @GET("product/select?categoryId=null&manufacturerId=null&hasOffer=null&PageNumber=null&RowCountPerPage=null&customerId=")
    Call<List<ProductDetails>> getOfferDetails(@Query("productId") int productId);

    @GET("relatedproduct/select")
    Call<List<RelatedProducts>> getRelated(@Query("productId") int productId, @Query("customerId") int customerId);

    @GET("product/select?categoryId=null&manufacturerId=null&productId=null&hasOffer=true&PageNumber=null&RowCountPerPage=null&customerId=")
    Call<List<ProductDetails>> getOffers();

    ///https://api.missvenue.com/en-US/api/banners/products?MetaTitle=Ramadan2020&customerId=22926
    @GET("banners/products?customerId=")
    Call<List<ProductDetails>> getOffersMetaTitle(@Query("MetaTitle") String MetaTitle);

    @FormUrlEncoded
    @POST("address/add")
    Call<address_add_model> ADDRESS_ADD_MODEL_CALL(
            @Field("customerid")
                    int customerid,
            @Field("firstName")
                    String firstName,
            @Field("lastName")
                    String lastName,
            @Field("email")
                    String email,
            @Field("countryId")
                    int countryId,
            @Field("city")
                    String city,
            @Field("stateProvinceId")
                    int stateProvinceId,
            @Field("address1")
                    String address1,
            @Field("ZipPostalCode")
                    String ZipPostalCode,
            @Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @POST("address/update")
    Call<address_add_model> Update_ADD_MODEL_CALL(@Field("customerid")
                                                          int customerid,
                                                  @Field("AddressId")
                                                          int AddressId,
                                                  @Field("firstName")
                                                          String firstName,
                                                  @Field("lastName")
                                                          String lastName,
                                                  @Field("email")
                                                          String email,
                                                  @Field("countryId")
                                                          int countryId,
                                                  @Field("city")
                                                          String city,
                                                  @Field("stateProvinceId")
                                                          int stateProvinceId,
                                                  @Field("address1")
                                                          String address1,
                                                  @Field("ZipPostalCode")
                                                          String ZipPostalCode,
                                                  @Field("phoneNumber") String phoneNumber);

    @GET("address/country_list?Id=null")
    Call<List<Country_Model>> COUNTRY_MODEL_CALL();

    @GET("customer/address")
    Call<List<User_address_model>> User_address_model(@Query("Customer_Id") int Customer_Id);

    @GET("customer/address")
    Call<List<User_address_model>> User_address_model(@Query("Customer_Id") int Customer_Id,
                                                      @Query("Address_Id") int Address_Id);

    @GET("search/product?CustomerId=null")
    Call<List<SearchProductCategory>> SearchProductCategoryCall(

            @Query("searchword") String searchword, @Query("PageNumber") int PageNumber, @Query("RowCountPerPage") int RowCountPerPage);

    @GET("address/state_list?Id=null")
    Call<List<City_model>> LIST_CALLCity_model(@Query("country_id") int country_id);

    @GET("Review/list")
    Call<ReviewModel> LIST_ReviewModel_model(@Query("productId") int productId);

    @FormUrlEncoded
    @POST("cart/add")
    Call<Add_to_cart> addToCart(@Field("ShoppingCartTypeId") int type,
                                @Field("customerId") int customerId,
                                @Field("productId") int productId,
                                @Field("quantity") int quantity);

    @FormUrlEncoded
    @POST("Review/add")
    Call<Add_to_cart> addReview(@Field("CustomerId") int customerId,
                                @Field("ProductId") int productId,
                                @Field("Review") String Review,
                                @Field("Rate") int Rate);

    @FormUrlEncoded
    @POST("Rate/add")
    Call<Add_to_cart> addRate(@Field("CustomerId") int customerId,
                              @Field("ProductId") int productId,
                              @Field("Rate") int Rate);

    @FormUrlEncoded
    @POST("customer/update")
    Call<customer_update_model> CUSTOMER_UPDATE_MODEL_CALL(@Field("customerid") int customerid,
                                                           @Field("phone") String phone,
                                                           @Field("email") String email,
                                                           @Field("firstname") String firstname,
                                                           @Field("lastname") String lastname
    );

    @GET("Order/Details")
    Call<OrdersModel> getItemsOrders(@Query("orderId") int orderId);

    @FormUrlEncoded
    @POST("cart/update")
    Call<Add_to_cart> updateCart(@Field("ShoppingCartTypeId") int type,
                                 @Field("customerId") int customerId,
                                 @Field("productId") int productId,
                                 @Field("quantity") int quantity);

    @FormUrlEncoded
    @POST("password/update")
    Call<Add_to_cart> changePassword(@Field("customerid") int customerid,
                                     @Field("oldpassword") String oldpassword,
                                     @Field("newpassword") String newpassword);


    @GET("banners/home")
    Call<Banners> getBanners(@Query("sector_id") int sector_id, @Query("entityType") int entityType);

    @GET("orderProduct/Check")
    Call<List<OrderCheck>> orderCheck(@Query("CustomerId") int CustomerId);

    @GET("verification/Resend")
    Call<OrderCheck> verifyEmail(@Query("Customerid") int CustomerId,@Query("Email") String email);

    @GET("OrderItems/Update")
    Call<Add_to_cart> orderItemUpdate(@Query("CustomerId") int CustomerId);

    @GET("Splash/Screen")
    Call<SplashModel> SPLASH_MODEL_CALL();

    @GET("StaticPage/{page}")
    Call<StaticsModel> staticPage(@Path("page") String name);

    @FormUrlEncoded
    @POST("Guest/session")
    Call<Guest_session> GUEST_SESSION_CALL(@Field("Guestsession") String Guestsession);

    @FormUrlEncoded
    @POST("Notification/Add_Details")
    Call<Guest_session> sendAddressFcm(@Field("Customer_Id") int Customer_Id,
                                       @Field("Phone_Type") String Phone_Type,
                                       @Field("FCM") String FCM,
                                       @Field("Area") String Area);
}