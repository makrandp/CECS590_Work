package edu.csulb.android.tacoorderingapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    static int total = 0;
    Button order;
    CheckBox beef,rice,chicken,beans,wfish,pico,cheese,seafood,lbt,gua,soda,margarita,tequilla,cerveza;
    RadioButton large,medium,corn,flour;
    HashMap<String,Integer> size = new HashMap<>();
    HashMap<String,Integer> fillings = new HashMap<>();
    HashMap<String,Integer> beverage = new HashMap<>();
    HashMap<String,Integer> tortilla = new HashMap<>();
    private static final int REQUEST_PERMISSIONS = 20;
    private static String orderDetails ="";
    private static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        order = (Button) findViewById(R.id.buttonOrder);

        beef=(CheckBox)findViewById(R.id.checkBoxBeef);
        rice=(CheckBox)findViewById(R.id.checkBoxRice);
        chicken=(CheckBox)findViewById(R.id.checkBoxChicken);
        beans=(CheckBox)findViewById(R.id.checkBoxBeans);
        wfish=(CheckBox)findViewById(R.id.checkBoxWFish);
        pico=(CheckBox)findViewById(R.id.checkBoxPico);
        cheese=(CheckBox)findViewById(R.id.checkBoxCheese);
        gua=(CheckBox)findViewById(R.id.checkBoxGuacamole);
        seafood=(CheckBox)findViewById(R.id.checkBoxSeaFood);
        lbt=(CheckBox)findViewById(R.id.checkBoxLBT);
        soda=(CheckBox)findViewById(R.id.checkBoxSoda);
        margarita=(CheckBox)findViewById(R.id.checkBoxMargarita);
        cerveza=(CheckBox)findViewById(R.id.checkBoxCerveza);
        tequilla=(CheckBox)findViewById(R.id.checkBoxTequilla);

        large= (RadioButton) findViewById(R.id.radioButtonLarge);
        medium= (RadioButton) findViewById(R.id.radioButtonMedium);
        corn= (RadioButton) findViewById(R.id.radioButtonCorn);
        flour= (RadioButton) findViewById(R.id.radioButtonFlour);


        beef.setOnCheckedChangeListener(this);
        rice.setOnCheckedChangeListener(this);
        chicken.setOnCheckedChangeListener(this);
        beans.setOnCheckedChangeListener(this);
        wfish.setOnCheckedChangeListener(this);
        pico.setOnCheckedChangeListener(this);
        cheese.setOnCheckedChangeListener(this);
        gua.setOnCheckedChangeListener(this);
        seafood.setOnCheckedChangeListener(this);
        lbt.setOnCheckedChangeListener(this);
        soda.setOnCheckedChangeListener(this);
        margarita.setOnCheckedChangeListener(this);
        cerveza.setOnCheckedChangeListener(this);
        tequilla.setOnCheckedChangeListener(this);

        large.setOnCheckedChangeListener(this);
        medium.setOnCheckedChangeListener(this);
        corn.setOnCheckedChangeListener(this);
        flour.setOnCheckedChangeListener(this);



        order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(total==0 || flag==false){
                    if(large.isChecked()){
                        total+=5;
                        size.put("Large",1);
                        Log.d("Message","InONCLICKLARGE");
                        flag=true;

                    }else if(medium.isChecked()){
                        total+=3;
                        size.put("Medium",1);
                        flag=true;
                    }

                    if(corn.isChecked()){
                        total+=3;
                        tortilla.put("Corn",1);
                        flag=true;
                    }else if(flour.isChecked()){
                        total+=2;
                        tortilla.put("Flour",1);
                        flag=true;
                    }

                }

                orderDetails ="I WANT A BIG TACO - ";
                StringBuilder sizeSb = new StringBuilder();
                Integer intValue=1;

                for(Map.Entry entry: size.entrySet()){
                    if(intValue.equals(entry.getValue())){
                        sizeSb.append(entry.getKey());
                    }
                }
                orderDetails = orderDetails + sizeSb.toString()+" Size.";

                StringBuilder tortillaSb = new StringBuilder();
                //     Integer intValue=1;

                for(Map.Entry entry: tortilla.entrySet()){
                    if(intValue.equals(entry.getValue())){
                        //  tortillaSb.append(" ");
                        tortillaSb.append(entry.getKey());
                    }
                }
                orderDetails = orderDetails + " With " +tortillaSb.toString()+".";

                StringBuilder fillingsSb = new StringBuilder();
                //     Integer intValue=1;

                for(Map.Entry entry: fillings.entrySet()){
                    if(intValue.equals(entry.getValue())){
                        fillingsSb.append(entry.getKey());
                        fillingsSb.append(", ");
                    }
                }

                orderDetails = orderDetails + " With " +  fillingsSb.toString()+".";

                StringBuilder beverageSb = new StringBuilder();
                //     Integer intValue=1;

                for(Map.Entry entry: beverage.entrySet()){
                    if(intValue.equals(entry.getValue())){
                        beverageSb.append(entry.getKey());
                        beverageSb.append(", ");
                    }
                }


                orderDetails = orderDetails + " With " +  beverageSb.toString()+".";
                orderDetails = orderDetails + " Total Price is " +total +" $";
                //     Toast.makeText(getApplicationContext(),orderDetails,Toast.LENGTH_SHORT).show();

                checkForPermission();
            }
        });

    }


    private void checkForPermission() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale (MainActivity.this, Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission
                                .SEND_SMS},
                        REQUEST_PERMISSIONS);
            }
        } else {
            //Call whatever you want
            sendMessage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if ((grantResults.length > 0) && (grantResults[0] +
                        grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
                    //Call whatever you want
                    sendMessage();
                } else {

                }
                return;
            }
        }
    }

    private void sendMessage() {

        SmsManager smsmanager = SmsManager.getDefault();

        //orderDetails = orderDetails + " Total Price is " +total +" $";
        smsmanager.sendTextMessage("5628507085",null,orderDetails,null,null);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean checked) {

        switch (buttonView.getId()){

            case R.id.checkBoxBeef :
                if(checked){
                    total+=1;
                    fillings.put("Beef",1);
                }
                else{
                    total-=1;
                    fillings.put("Beef",0);
                }
                break;
            case R.id.checkBoxRice :
                if(checked){
                    total+=1;
                    fillings.put("Rice",1);
                }
                else{
                    total-=1;
                    fillings.put("Rice",0);
                }
                break;

            case R.id.checkBoxChicken :
                if(checked){
                    total+=1;
                    fillings.put("Chicken",1);
                }
                else{
                    total-=1;
                    fillings.put("Chicken",0);
                }
                break;

            case R.id.checkBoxBeans :
                if(checked){
                    total+=1;
                    fillings.put("Beans",1);
                }
                else{
                    total-=1;
                    fillings.put("Beans",0);
                }
                break;

            case R.id.checkBoxWFish :
                if(checked){
                    total+=1;
                    fillings.put("White Fish",1);
                }
                else{
                    total-=1;
                    fillings.put("White Fish",0);
                }
                break;

            case R.id.checkBoxPico :
                if(checked){
                    total+=1;
                    fillings.put("Pico de Gallo",1);
                }
                else{
                    total-=1;
                    fillings.put("Pico de Gallo",0);
                }
                break;

            case R.id.checkBoxCheese :
                if(checked){
                    total+=1;
                    fillings.put("Cheese",1);
                }
                else{
                    total-=1;
                    fillings.put("Cheese",0);
                }
                break;

            case R.id.checkBoxSeaFood :
                if(checked){
                    total+=1;
                    fillings.put("Sea Food",1);
                }
                else{
                    total-=1;
                    fillings.put("Sea Food",0);
                }
                break;

            case R.id.checkBoxGuacamole :
                if(checked){
                    total+=1;
                    fillings.put("Gaucamole",1);
                }
                else{
                    total-=1;
                    fillings.put("Gaucamole",0);
                }
                break;

            case R.id.checkBoxLBT :
                if(checked){
                    total+=1;
                    fillings.put("LBT",1);
                }
                else{
                    total-=1;
                    fillings.put("LBT",0);
                }
                break;

            case R.id.checkBoxSoda :
                if(checked){
                    total+=1;
                    beverage.put("Soda",1);
                }
                else{
                    total-=1;
                    beverage.put("Soda",0);
                }
                break;

            case R.id.checkBoxCerveza :
                if(checked){
                    total+=1;
                    beverage.put("Cerveza",1);
                }
                else{
                    total-=1;
                    beverage.put("Cerveza",0);
                }
                break;

            case R.id.checkBoxMargarita :
                if(checked){
                    total+=1;
                    beverage.put("Margarita",1);
                }
                else{
                    total-=1;
                    beverage.put("Margarita",0);
                }
                break;
            case R.id.checkBoxTequilla :
                if(checked){
                    total+=1;
                    beverage.put("Tequilla",1);
                }
                else{
                    total-=1;
                    beverage.put("Tequilla",0);
                }
                break;

            case R.id.radioButtonLarge :
                if(checked){
                    total += 5;
                    size.put("Large",1);
                    flag=true;
                }
                else{
                    total-=5;
                    size.put("Large",0);
                }
                break;

            case R.id.radioButtonMedium :
                if(checked){
                    total += 3;
                    size.put("Medium",1);
                    flag=true;
                }
                else{
                    total-=3;
                    size.put("Medium",0);
                }
                break;

            case R.id.radioButtonCorn :
                if(checked){
                    total += 3;
                    tortilla.put("Corn",1);
                    flag=true;
                }
                else{
                    total-=3;
                    tortilla.put("Corn",0);
                }
                break;

            case R.id.radioButtonFlour :
                if(checked){
                    total += 2;
                    tortilla.put("Flour",1);
                    flag=true;
                }
                else{
                    total-=2;
                    tortilla.put("Flour",0);
                }
                break;

        }

    }



}
