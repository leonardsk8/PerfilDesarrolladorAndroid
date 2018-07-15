package leonard.ilioncorp.co.perfildesarrolladorandroid.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import leonard.ilioncorp.co.perfildesarrolladorandroid.R;

public class DesignCarActivity extends AppCompatActivity implements View.OnClickListener
,AdapterView.OnItemSelectedListener{

    private android.widget.LinearLayout layoutWheels;
    private android.widget.Spinner spColorWheels;
    private android.widget.LinearLayout layoutCanvasWheel;
    private android.widget.LinearLayout layoutHood;
    private android.widget.Spinner spColorHood;
    private android.widget.LinearLayout layoutCanvasHood;
    private android.widget.LinearLayout layoutDoor;
    private android.widget.Spinner spColorDoor;
    private android.widget.LinearLayout layoutCanvasDoor;
    private android.support.v7.widget.CardView cvBtnSaveColorCar;

    public static final int WITHOUT_COLOR = 1;
    public static final int COLOR_BLUE = 2;
    public static final int COLOR_YELLOW = 3;
    public static final int COLOR_GREEN = 4;
    private int colorDoors;
    private int colorHoods;
    private int colorWheels;
    private int layoutWheelWidthX;
    private int layoutWheelHeightY;
    private int layoutHoodWidthX;
    private int layoutHoodHeightY;
    private int layoutDoorWidthX;
    private int layoutDoorHeightY;
    private Wheels pcc;
    private Hood hood;
    private  Door door;
    private Canvas canvasWheel;
    private int contaux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_car);
        this.cvBtnSaveColorCar = findViewById(R.id.cvBtnSaveColorCar);
        this.layoutCanvasDoor = findViewById(R.id.layoutCanvasDoor);
        this.spColorDoor = findViewById(R.id.spColorDoor);
        this.layoutDoor = findViewById(R.id.layoutDoor);
        this.layoutCanvasHood = findViewById(R.id.layoutCanvasHood);
        this.spColorHood =  findViewById(R.id.spColorHood);
        this.layoutHood = findViewById(R.id.layoutHood);
        this.layoutCanvasWheel = findViewById(R.id.layoutCanvasWheel);
        this.spColorWheels = findViewById(R.id.spColorWheels);
        this.layoutWheels = findViewById(R.id.layoutWheels);
        
        pcc = new Wheels (this);
        hood = new Hood(this);
        door = new Door(this);
        this.colorDoors = getIntent().getExtras().getInt("doors");
        this.colorHoods = getIntent().getExtras().getInt("hoods");
        this.colorWheels = getIntent().getExtras().getInt("wheels");
        //fillColors();
        String[] colors;
        //if(colorDoors.isEmpty() & colorHoods.isEmpty() & colorWheels.isEmpty())
        colors = new String[]{"Color","Sin color","Azul","Amarillo","Verde"};

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, colors);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spColorDoor.setAdapter(aa);
        this.spColorHood.setAdapter(aa);
        this.spColorWheels.setAdapter(aa);
        this.spColorDoor.setOnItemSelectedListener(this);
        this.spColorHood.setOnItemSelectedListener(this);
        this.spColorWheels.setOnItemSelectedListener(this);
        startCanvas();

        this.cvBtnSaveColorCar.setOnClickListener(this::onClick);


    }

    private void fillColors() {
     switch (colorWheels){
         case WITHOUT_COLOR:
             colorWheels = Color.WHITE;
             break;
         case COLOR_BLUE:
             colorWheels = Color.BLUE;
             break;
         case COLOR_YELLOW:
             colorWheels = Color.YELLOW;
             break;
         case COLOR_GREEN:
             colorWheels = Color.GREEN;
             break;
     }
     switch (colorHoods){
         case WITHOUT_COLOR:
             colorHoods = Color.WHITE;
             break;
         case COLOR_BLUE:
             colorHoods = Color.BLUE;
             break;
         case COLOR_YELLOW:
             colorHoods = Color.YELLOW;
             break;
         case COLOR_GREEN:
             colorHoods = Color.GREEN;
             break;
     }
     switch (colorDoors){
         case WITHOUT_COLOR:
             colorDoors = Color.WHITE;
             break;
         case COLOR_BLUE:
             colorDoors = Color.BLUE;
             break;
         case COLOR_YELLOW:
             colorDoors = Color.YELLOW;
             break;
         case COLOR_GREEN:
             colorDoors = Color.GREEN;
             break;
     }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("doors",colorDoors);
        intent.putExtra("hoods",colorHoods);
        intent.putExtra("wheels",colorWheels);
        setResult(RESULT_OK,intent);
        //close this Activity...
        finish();

    }

    public void startCanvas() {
        ViewTreeObserver observer = this.layoutCanvasWheel.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutWheelWidthX= layoutCanvasWheel.getWidth();
                layoutWheelHeightY = layoutCanvasWheel.getHeight();
                layoutCanvasWheel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                addCanvasWheel(colorWheels);
            }
        });

        ViewTreeObserver observer1 = this.layoutCanvasHood.getViewTreeObserver();
        observer1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutHoodWidthX= layoutCanvasHood.getWidth();
                layoutHoodHeightY = layoutCanvasHood.getHeight();
                layoutCanvasHood.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                addCanvasHood(colorHoods);
            }
        });


        ViewTreeObserver observer2 = this.layoutCanvasDoor.getViewTreeObserver();
        observer2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutDoorWidthX = layoutCanvasDoor.getWidth();
                layoutDoorHeightY = layoutCanvasDoor.getHeight();
                addCanvasDoor(colorDoors);
                layoutCanvasDoor.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    private void addCanvasWheel(int color) {
        if(pcc!=null)
            this.layoutCanvasWheel.removeView(pcc);
        Bitmap result = Bitmap.createBitmap(layoutWheelWidthX, layoutWheelHeightY, Bitmap.Config.ARGB_8888);
        canvasWheel = new Canvas(result);
        pcc.paintCanvasWheel(canvasWheel,color);
        pcc.setLayoutParams(new LinearLayout.LayoutParams(layoutWheelWidthX, layoutWheelHeightY));
        this.layoutCanvasWheel.addView(pcc);

    }

    private void addCanvasDoor(int color) {
        if(door!=null)
            this.layoutCanvasDoor.removeView(door);
        Bitmap result = Bitmap.createBitmap(layoutDoorWidthX, layoutDoorHeightY, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);

        door.paintCanvasDoor(canvas,color);
        door.setLayoutParams(new LinearLayout.LayoutParams(layoutDoorWidthX, layoutDoorHeightY));
        this.layoutCanvasDoor.addView(door);
    }

    private void addCanvasHood(int color) {
        if(hood!=null)
            this.layoutCanvasHood.removeView(hood);
        Bitmap result = Bitmap.createBitmap(layoutHoodWidthX, layoutHoodHeightY, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        hood.paintCanvasHood(canvas,color);
        hood.setLayoutParams(new LinearLayout.LayoutParams(layoutHoodWidthX, layoutHoodHeightY));
        this.layoutCanvasHood.addView(hood);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();
        if(contaux>=3) {
            switch (id) {
                case R.id.spColorDoor:
                    colorDoors = i;
                    fillColors();
                    addCanvasDoor(colorDoors);
                    break;
                case R.id.spColorHood:
                    colorHoods = i;
                    fillColors();
                    addCanvasHood(colorHoods);
                    break;
                case R.id.spColorWheels:
                    colorWheels = i;
                    fillColors();
                    addCanvasWheel(colorWheels);
                    break;
            }

        }
        contaux++;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class Wheels extends View {

        int color;
        public Wheels(Context context) {
            super(context);
            // TODO Auto-generated constructor stub


        }
        public void paintCanvasWheel(Canvas canvas,int color){
            this.color = color;
            draw(canvas);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);

            int width = canvas.getWidth();
            int height = canvas.getHeight();
            int minus = 0;
            Paint paint = new Paint();

            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle((float) (width/2), (float) (height/2)-minus,width/5,paint);

            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);

            Log.e("Width",width+"");
            Log.e("Heigh",width+"");
            canvas.drawCircle((float) (width/2), (float) (height/2)-minus,width/5,paint);
            canvas.drawCircle((float) (width/2), (float) (height/2)-minus,width/8,paint);

            //canvas.drawLine(132,132,width,height,paint);
            //canvas.drawRect(width/2, height/2, width, height, paint);
        }
    }

    private class Door extends View {
        int color;
        public Door(Context context) {
            super(context);
            color = Color.WHITE;
        }
        public void paintCanvasDoor(Canvas canvas,int color){
            this.color = color;
            draw(canvas);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            Path path = new Path();
            path.moveTo(width/4, height/2);
            path.lineTo( width/4, (float) (height/1.3333));
            path.lineTo((float) (width/1.3333), (float) (height/1.3333));
            path.lineTo((float) (width/1.3333),height/2-60);
            path.lineTo((float) (width/1.875), height/2-60);
            path.lineTo(width/4, height/2);
            path.close();

            Paint paint=new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(5);
            canvas.drawPath(path, paint);

            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(8);
            canvas.drawLine(width/4,height/2,width/4, (float) (height/1.3333),paint);
            canvas.drawLine(width/4, (float) (height/1.3333), (float) (width/1.3333), (float) (height/1.3333),paint);
            canvas.drawLine((float) (width/1.3333), (float) (height/1.3333), (float) (width/1.3333), height/2-60,paint);
            canvas.drawLine((float) (width/1.3333), height/2-60, (float) (width/1.875), height/2-60,paint);
            canvas.drawLine((float) (width/1.875), height/2-60, (float) (width/4), height/2,paint);

        }
    }
    private class Hood extends View{
        int color;
        public Hood(Context context){
            super(context);
            color = Color.WHITE;
        }

        public void paintCanvasHood(Canvas canvas,int color){
            this.color = color;
            draw(canvas);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int width = canvas.getWidth();
            int height = canvas.getHeight();


            Path path = new Path();
            path.moveTo(width/4, height/2);
            path.lineTo(width/4 ,height/2+30);
            path.lineTo(width/2, height/2+60);
            path.lineTo(width/2 ,height/2-60);
            path.lineTo(width/4, height/2);
            path.moveTo((float) (width/1.3333), height/2);
            path.lineTo((float) (width/1.3333),height/2+30);
            path.lineTo(width/2+25, height/2+60);
            path.lineTo(width/2+25 ,height/2-60);
            path.lineTo((float) (width/1.3333), height/2);

            path.close();

            Paint paint=new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(5);
            canvas.drawPath(path, paint);

            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(8);
            canvas.drawLine(width/4,height/2,width/4,height/2+30,paint);
            canvas.drawLine(width/4,height/2+30,width/2,height/2+60,paint);
            canvas.drawLine(width/2,height/2+60,width/2,height/2-60,paint);
            canvas.drawLine(width/2,height/2-60,width/4,height/2,paint);

            canvas.drawLine((float) (width/1.3333),height/2, (float) (width/1.3333),height/2+30,paint);
            canvas.drawLine((float) (width/1.3333),height/2+30,width/2+25,height/2+60,paint);
            canvas.drawLine(width/2+25,height/2+60,width/2+25,height/2-60,paint);
            canvas.drawLine(width/2+25,height/2-60, (float) (width/1.3333),height/2,paint);


        }
    }
}
