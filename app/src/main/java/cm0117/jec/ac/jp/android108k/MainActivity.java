package cm0117.jec.ac.jp.android108k;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    TextView txtPrice;
    //カンマ区切りインスタンス
    //NumberFormat kanma = NumberFormat.getNumberInstance();
    //地域設定の通貨のマークを表示$¥
    NumberFormat moneyUnit = NumberFormat.getCurrencyInstance();

    String tValue = "0";//押した数字の集まり
    String value = "0";//押したボタンの数字
    String formatValue;



    class NumberClickListnerImple implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            //押されたボタンのインスタンスがやってくる
            //TextViewの文字を取得する
            tValue = txtPrice.getText().toString();
            //押されたボタンの文字を取得する
            Button button = (Button) v;
            value = button.getText().toString();

            //カンマつけたり¥つけたりするメソッド
            //表示もする
            formatNumber();
        }
    }

    /**
     * Cボタンが押された時にそれまでの数字を消去する
     */
    class NumberClearListnerImple implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            txtPrice.setText(String.valueOf(0));

        }
    }
    /**
     * zero
     */
    class  ZeroClickListnerImple implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            tValue = txtPrice.getText().toString();
            Button button = (Button)v;
            value = button.getText().toString();

            //カンマつけたり¥つけたりするメソッド
            //表示もする
            formatNumber();
        }
    }


    /**
     * SeekBar用のイベントリスナークラス（イベントハンドラークラスとも言う）
     */
    class SeekBarControll implements SeekBar.OnSeekBarChangeListener{
        private int discount = 0;

        /**
         * seekBarをずらしている時に毎回呼ばれるメソッド
         * @param seekBar SeekBarのインスタンス
         * @param progress SeekBarで選択された値
         * @param fromUser ユーザーが操作した時に呼ばれたのかシステム上で設定された時に呼ばれたのか(true of false)
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            TextView text = (TextView)findViewById(R.id.txtDiscount);
            discount = (progress*10)/2; //割引率を0〜100％で表示するため
            text.setText(String.valueOf(discount));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            //特に無し
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Toast.makeText(MainActivity.this, discount + "%の割引をします", Toast.LENGTH_SHORT).show();

            //double price = Double.valueOf(txtPrice.getText().toString());
            double price = Double.valueOf(formatValue);


            //支払いの計算
            double pay = price - (price * ((double)discount / 100));
            Log.i("android108k","元の値段" + price + "円" + "支払額=" + pay + "円" + "discount" + discount);
            //支払額表示
            TextView txtPaym = (TextView)findViewById(R.id.txtPayment);
            txtPaym.setText(String.valueOf((int)pay));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //NumberClickのインスタンス化
        int[] btnIds = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        for (int i = 0; i < btnIds.length; i++){
            Button btn = (Button)findViewById(btnIds[i]);
            btn.setOnClickListener(new NumberClickListnerImple());
        }
        //Cボタンのインスタンス化
        Button btnClear = (Button)findViewById(R.id.btnC);
        btnClear.setOnClickListener(new NumberClearListnerImple());

        //シークバーのインスタンス化
        SeekBar bar = (SeekBar)findViewById(R.id.skbDiscount);
        bar.setOnSeekBarChangeListener(new SeekBarControll());

        txtPrice = (TextView)findViewById(R.id.txtPrice);

        //Zero関連付け
        int[] btnZeros = {R.id.btn0, R.id.btn00};
        for (int a = 0; a < btnZeros.length; a++){
            Button btnZero = (Button)findViewById(btnZeros[a]);
            btnZero.setOnClickListener(new ZeroClickListnerImple());
        }
        //最初に押した数字が反映されないのに対処
        formatNumber();
        //文字数カウントでformatValue.length()８桁までに計算を制限させる

    }
    public void formatNumber(){

        //TextViewの文字に押されたボタンの文字を連結する
        tValue = tValue + value;

        clearMarks();

        if (formatValue.equals("")){
            formatValue = "0";
        }
        //0を先頭から外す
        if (tValue.equals("0")){
            tValue = "";
        }
        //String to Integer
        int intValue = Integer.parseInt(formatValue);
        //System.out.println(moneyUnit.format(intValue));
        txtPrice.setText(moneyUnit.format(intValue));
    }
    public void clearMarks(){
        formatValue = tValue.replace(",","");
        //formatValue = formatValue.replace("¥","");
        formatValue = formatValue.substring(1,formatValue.length());
    }

}
