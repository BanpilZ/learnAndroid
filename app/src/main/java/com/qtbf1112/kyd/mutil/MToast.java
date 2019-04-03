package com.fkgpby0329.yxb.mutil;
import android.annotation.SuppressLint;
import android.content.*;
import android.widget.*;

public class MToast
{
	
	@SuppressLint("WrongConstant")
	public void mToast(Context c, String s){
		Toast.makeText(c,s,3000).show();
		
		
	}
}
