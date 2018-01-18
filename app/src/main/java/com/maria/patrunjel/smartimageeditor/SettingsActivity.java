package com.maria.patrunjel.smartimageeditor;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends Activity {

    private Integer redValue,greenValue,blueValue,contrast;
    private Float brightness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();

        redValue = intent.getIntExtra("red",0)/10;
        greenValue = intent.getIntExtra("green",0)/10;
        blueValue = intent.getIntExtra("blue",0)/10;
        brightness =  intent.getFloatExtra("brightness",1.0f);
        contrast =  intent.getIntExtra("contrast",0);


        SeekBar redBar = (SeekBar)findViewById(R.id.redBar);
        SeekBar greenBar = (SeekBar)findViewById(R.id.greenBar);
        SeekBar blueBar = (SeekBar)findViewById(R.id.blueBar);
        SeekBar brightnessBar = (SeekBar)findViewById(R.id.brightnessBar);
        SeekBar contrastBar = (SeekBar)findViewById(R.id.contrastBar);

        redBar.setProgress(redValue+25);
        greenBar.setProgress(greenValue+25);
        blueBar.setProgress(blueValue+25);
        contrastBar.setProgress(contrast+100);
        brightnessBar.setProgress((int)((brightness+0.1)*10));

        TextView red = (TextView)findViewById(R.id.redValue);
        TextView green = (TextView)findViewById(R.id.greenValue);
        TextView blue = (TextView)findViewById(R.id.blueValue);

        red.setText(Integer.toString(redValue*10));
        green.setText(Integer.toString(greenValue*10));
        blue.setText(Integer.toString(blueValue*10));

        setSeekBarsListeners();

    }

    public void onClickBackButton(View view){
        finish();
    }

    public void onClickResetButton(View view){
        redValue = 0;
        greenValue = 0;
        blueValue = 0;
        brightness = 1.0f;
        contrast = 0;

        SeekBar redBar = (SeekBar)findViewById(R.id.redBar);
        SeekBar greenBar = (SeekBar)findViewById(R.id.greenBar);
        SeekBar blueBar = (SeekBar)findViewById(R.id.blueBar);
        SeekBar brightnessBar = (SeekBar)findViewById(R.id.brightnessBar);
        SeekBar contrastBar = (SeekBar)findViewById(R.id.contrastBar);

        redBar.setProgress(redValue+25);
        greenBar.setProgress(greenValue+25);
        blueBar.setProgress(blueValue+25);
        contrastBar.setProgress(contrast+100);
        brightnessBar.setProgress((int)((brightness+0.1)*10));
    }

    @Override
    public void finish() {
        // Prepare data intent

        Intent data = new Intent();
        data.putExtra("red",redValue*10);
        data.putExtra("green",greenValue*10);
        data.putExtra("blue",blueValue*10);
        data.putExtra("contrast",contrast);
        data.putExtra("brightness",brightness);

        setResult(RESULT_OK, data);
        super.finish();
    }

    private void setSeekBarsListeners(){

        SeekBar redBar = (SeekBar)findViewById(R.id.redBar);
        SeekBar greenBar = (SeekBar)findViewById(R.id.greenBar);
        SeekBar blueBar = (SeekBar)findViewById(R.id.blueBar);
        SeekBar brightnessBar = (SeekBar)findViewById(R.id.brightnessBar);
        SeekBar contrastBar = (SeekBar)findViewById(R.id.contrastBar);

        redBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                redValue = progress - 25;
                TextView red = (TextView) findViewById(R.id.redValue);
                red.setText(Integer.toString(redValue * 10));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        greenBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                greenValue =progress-25;
                TextView green = (TextView)findViewById(R.id.greenValue);
                green.setText(Integer.toString(greenValue*10));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub


            }
        });

        blueBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blueValue =progress-25;
                TextView blue = (TextView)findViewById(R.id.blueValue);
                blue.setText(Integer.toString(blueValue*10));

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

                // TODO Auto-generated method stub

            }
        });

        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightness=(((float) progress)-0.1f)/10;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub


            }
        });

        contrastBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                contrast= progress-100;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub


            }
        });

    }

}


