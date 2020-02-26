//package com.ibsvalleyn.missvenue.fragments.CheckOut;
//
//import android.app.AlertDialog;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.gson.Gson;
//import com.ibsvalleyn.missvenue.R;
//import com.ibsvalleyn.missvenue.activities.CheckOutActivity;
//import com.ibsvalleyn.missvenue.adapters.MyOrdersAdapter;
//import com.ibsvalleyn.missvenue.adapters.PersonAdapter;
//import com.ibsvalleyn.missvenue.adapters.richContentAdapter;
//import com.ibsvalleyn.missvenue.api.RetrofitClient;
//import com.ibsvalleyn.missvenue.helper.Static_variables;
//import com.ibsvalleyn.missvenue.models.OrderView.OrderView;
//import com.kaopiz.kprogresshud.KProgressHUD;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static android.preference.PreferenceManager.getDefaultSharedPreferences;
//import static com.ibsvalleyn.missvenue.helper.AppFunctions.getKProgressHUD;
//
//public class PaymentCheckoutFragment extends Fragment {
//    RecyclerView myOrdersRecyclerView;
//    GridLayoutManager layoutManager;
//    MyOrdersAdapter myOrdersAdapter;
//    SharedPreferences dataSaver;
//    int id;
//    private KProgressHUD kProgressHUD;
//    private Spinner CitySp;
//
//    List<String> Governments = new ArrayList<>();
//    List<String> Govern_IDs = new ArrayList<>();
//    private String City_id;
//    private TextView add_address;
//    private AlertDialog builder;
//    private int CUSTOMER_ID;
//    private static final String TAG = "kldjasdlkjsald";
//
//    private List<String> MainCats_name = new ArrayList<>();
//    private List<String> MainCats_IDs = new ArrayList<>();
//    private List<String> SubCats_IDs = new ArrayList<>();
//    private List<String> SubCats_name = new ArrayList<>();
//    private String subCatID;
//    private EditText first_name;
//    private EditText last_name;
//    private EditText EmailAddress;
//    private EditText SubMobileNumberCatSp;
//    private EditText address;
//    private EditText postalCode;
//    private String s;
//    private Spinner MainCatSp, SubCatSp;
//    private String Cites;
//    private Button add_to_cart;
//    CheckOutActivity activity;
//    private Spinner getAllAddress;
//    private String BillingId;
//    private String provName;
//    private TextView addressName;
//    private boolean pressToExit = false;
//    private RecyclerView rv_payment;
//    private WebView webView;
//    private RecyclerView rv_richContent;
//    private String ShppingId;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activity = (CheckOutActivity) getActivity();
//        kProgressHUD = getKProgressHUD(activity);
//
//
//
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.checkout_payment_fragment, container, false);
//
//        assert getArguments() != null;
//        Log.i(TAG, "onCreateView: " + BillingId);
//
//        ShppingId = getArguments().getString("ShppingId");
//        BillingId = getArguments().getString("BillingId");
//        view.findViewById(R.id.back).setOnClickListener(view1 -> activity.onBackPressed());
//
//
//        dataSaver = getDefaultSharedPreferences(activity);
//        id = dataSaver.getInt(Static_variables.CUSTOMER_ID, 0);
//
//
//        rv_payment = view.findViewById(R.id.rv_payment);
//        rv_richContent = view.findViewById(R.id.rv_richContent);
////        view.findViewById(R.id.sendTo).setOnClickListener(view1 -> activity.loadFragment(new ConfirmationCheckoutFragment(), ""));
////        webView = view.findViewById(R.id.webView);
////        add_to_cart = view.findViewById(R.id.add_to_cart);
////        getAllAddress = view.findViewById(R.id.getAllAddress);
////        addressName = view.findViewById(R.id.addressName);
////
////        addressName.setText("you select shaping is :" + itemId);
//
//
////        if (pressToExit) {
////            activity.runOnUiThread(() ->    getAllAddress.setSelection(getIndex(getAllAddress, itemId)));
////            (new Handler()).postDelayed((() ->
////                    pressToExit = false), 1000L);
////        }
//        getUserAddress();
//
////        rv_payment.setLayoutManager(new LinearLayoutManager(activity));
//        rv_richContent.setLayoutManager(new LinearLayoutManager(activity));
//
//
//
//        rv_payment.setLayoutManager(new LinearLayoutManager(activity));
//
//
//        return view;
//    }
//
//    public List<String> getUserAddress() {
//        kProgressHUD.show();
//
//        Call<OrderView> provincesCall = RetrofitClient.getInstance(activity).ORDER_VIEW_CALL();
//
//        provincesCall.enqueue(new Callback<OrderView>() {
//            @Override
//            public void onResponse(Call<OrderView> call, Response<OrderView> response) {
//
//                kProgressHUD.dismiss();
//
//                if (response.isSuccessful()) {
//                    assert response.body() != null;
//                    Log.i(TAG, "onResponse: " + new Gson().toJson(response.body()));
//
////                    rv_payment.setAdapter(new PayMeatAdapter(response.body().getPaymentMethods(), activity));
//                    rv_payment.setAdapter(new PersonAdapter(activity, response.body().getPaymentMethods(),ShppingId,BillingId));
//
//                    rv_richContent.setAdapter(new richContentAdapter(response.body().getRichContent(), activity));
//
//
//                } else {
////                    Toast.makeText(activity, R.string.server_error, Toast.LENGTH_SHORT).show();
////                    progressDialog.dismiss();
//
//                }
////
//            }
//
//            @Override
//            public void onFailure(Call<OrderView> call, Throwable t) {
//                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show();
//                kProgressHUD.dismiss();
//
//
//            }
//        });
//        pressToExit = true;
//
//
//        return Governments;
//    }
//
//
//}