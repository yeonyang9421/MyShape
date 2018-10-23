package kr.co.woobi.imyeon.myshape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static int LINE = 1, CIRCLE=2, RECTANGLE=3;
    static  int curShape = LINE;
    static int curColor= Color.DKGRAY;
    static List<MyShape> myShapes = new ArrayList<MyShape>();
    static  boolean isFinish = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0,1,0,"선그리기");
        menu.add(0,2,0,"원그리기");
        menu.add(0,3,0,"사각형 그리기");
        SubMenu subMenu = menu.addSubMenu("색상변경 >> ");
        subMenu.add(0,4,0,"빨강");
        subMenu.add(0,5,0,"파랑");
        subMenu.add(0,6,0,"초록");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case 1: curShape=LINE; return true;
            case 2: curShape=CIRCLE; return  true;
            case 3: curShape=RECTANGLE; return  true;
            case 4: curColor= Color.RED; return  true;
            case 5: curColor= Color.BLUE; return  true;
            case 6: curColor= Color.GREEN; return  true;
            default: return false;
        }

    }

    private static  class MyShape{
        int shapeType;
        int startX, startY, stopX, stopY;
        int color;
    }
static  class MyGraphicView extends View {
        int startX=-1, startY=-1, stopX=-1, stopY=-1;
        public MyGraphicView(Context context){
            super(context);
        }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    startX=(int)event.getX();
                    startY=(int)event.getY();
                    isFinish=false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    stopX=(int)event.getX();
                    stopY=(int)event.getY();
                    this.invalidate();
                    isFinish=false;
                    break;
                case MotionEvent.ACTION_UP:
                    MyShape shape=new MyShape();
                    shape.shapeType=curShape;
                    shape.startX=this.startX;
                    shape.startY=this.startY;
                    shape.stopX=this.stopX;
                    shape.stopY=this.stopY;
                    shape.color = curColor;
                    myShapes.add(shape);
                    isFinish=true;
                    invalidate();
                    break;
            }
            return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        for(int i=0; i<myShapes.size(); i++){
            MyShape shape = myShapes.get(i);
            paint.setColor(shape.color);
            switch(shape.shapeType){
                case LINE:
                    canvas.drawLine(shape.startX, shape.startY, shape.stopX, shape.stopY, paint);
                    break;
                case CIRCLE:
                    int radius=(int) Math.sqrt(Math.pow(shape.stopX-shape.startX,2)+Math.pow(shape.stopY-shape.startY,2));
                    canvas.drawCircle(shape.startX, shape.startY, radius,paint);
                    break;
                case RECTANGLE:
                Rect rect=new Rect(shape.startX, shape.startY, shape.stopX, shape.stopY);
                canvas.drawRect(rect, paint);
                    break;
                //case LINE: break;
            }
            if(isFinish==false){
                paint.setColor(curColor);
                switch (curShape){
                    case LINE:
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        break;
                    case CIRCLE:
                        int radius=(int) Math.sqrt(Math.pow(stopX-startX,2)+Math.pow(stopY-startY,2));
                        canvas.drawCircle(startX, startY, radius,paint);
                        break;
                    case RECTANGLE:
                        Rect rect=new Rect(startX, startY, stopX, stopY);
                        canvas.drawRect(rect, paint);
                        break;
                }
            }
        }
    }
    }
}
