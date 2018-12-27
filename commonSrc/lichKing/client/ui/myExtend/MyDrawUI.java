package lichKing.client.ui.myExtend;

import com.smartgwt.client.types.ArrowStyle;
import com.smartgwt.client.types.LineCap;
import com.smartgwt.client.types.LinePattern;
import com.smartgwt.client.widgets.drawing.DrawLabel;
import com.smartgwt.client.widgets.drawing.DrawLinePath;
import com.smartgwt.client.widgets.drawing.DrawPane;
import com.smartgwt.client.widgets.drawing.DrawRect;
import com.smartgwt.client.widgets.drawing.Point;

public class MyDrawUI {

	public static DrawRect addDrawRect(DrawPane drawPane,String title,int left,int top,int width,int height){

		DrawLabel rectLabel = new DrawLabel();
		rectLabel.setDrawPane(drawPane);
		rectLabel.setLeft(left);
		rectLabel.setTop(top+height+5);
		rectLabel.setContents(title);
		rectLabel.setFontSize(12);
		rectLabel.draw();

		DrawRect drawRect = new DrawRect();
		drawRect.setDrawPane(drawPane);
		drawRect.setLeft(left);
		drawRect.setTop(top);
		drawRect.setWidth(width);
		drawRect.setHeight(height);
		drawRect.draw();
		
		return drawRect;
	}


	public static void addDrawLinePath(DrawPane drawPane,Point start,Point end){
		getDrawLinePath(drawPane, start, end).draw();
	}
	
	public static void addDrawLinePathDouble(DrawPane drawPane,Point start,Point end){

		DrawLinePath drawLineP = getDrawLinePath(drawPane, start, end);
		drawLineP.setStartArrow(ArrowStyle.OPEN);
		drawLineP.draw();
	}
	
	public static DrawLinePath getDrawLinePath(DrawPane drawPane,Point start,Point end){

		DrawLinePath drawLineP = new DrawLinePath();
		drawLineP.setDrawPane(drawPane);
		drawLineP.setStartPoint(start);
		drawLineP.setEndPoint(end);
		drawLineP.setLineWidth(2);
		drawLineP.setLineCap(LineCap.ROUND);
		drawLineP.setLinePattern(LinePattern.SOLID);
		
		return drawLineP;
	}
	
	
	public static Point getLinePointR(DrawRect drawRect){
		return new Point(drawRect.getLeft()+drawRect.getWidth(),drawRect.getTop()+drawRect.getHeight()/2);
	}
	public static Point getLinePointL(DrawRect drawRect){
		return new Point(drawRect.getLeft(),drawRect.getTop()+drawRect.getHeight()/2);
	}
	public static Point getLinePointH(DrawRect drawRect){
		return new Point(drawRect.getLeft()+drawRect.getWidth()/2,drawRect.getTop());
	}
	public static Point getLinePointB(DrawRect drawRect){
		return new Point(drawRect.getLeft()+drawRect.getWidth()/2,drawRect.getTop()+drawRect.getHeight());
	}
	//猪只系谱图
	public static DrawRect addDrawPigTree(DrawPane drawPane,String title,int left,int top,int width,int height){

		DrawLabel rectLabel = new DrawLabel();
		rectLabel.setDrawPane(drawPane);
		rectLabel.setLeft(left+5);
		rectLabel.setTop(top+5);
		rectLabel.setContents(title);
		rectLabel.setFontSize(12);
		rectLabel.draw();

		DrawRect drawRect = new DrawRect();
		drawRect.setDrawPane(drawPane);
		drawRect.setLeft(left);
		drawRect.setTop(top);
		drawRect.setWidth(width);
		drawRect.setHeight(height);
		drawRect.draw();
		
		return drawRect;
	}
	//猪只系谱图
	public static DrawRect addDrawBox(DrawPane drawPane,String title,int titleLeft,int titleTop,int boxLeft,int boxTop,String fillColor,int width,int height){

			DrawLabel rectLabel = new DrawLabel();
			rectLabel.setDrawPane(drawPane);
			rectLabel.setLeft(titleLeft);
			rectLabel.setTop(titleTop);
			rectLabel.setContents(title);
			rectLabel.setFontSize(13);
			rectLabel.setFontWeight("normal");
			rectLabel.draw();

			DrawRect drawRect = new DrawRect();
			drawRect.setDrawPane(drawPane);
			drawRect.setLeft(boxLeft);
			drawRect.setTop(boxTop);
			drawRect.setWidth(width);
			drawRect.setHeight(height);
			drawRect.setFillColor(fillColor);
			drawRect.draw();
			
			return drawRect;
		}
}
