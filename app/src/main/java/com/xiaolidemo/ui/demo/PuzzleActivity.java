package com.xiaolidemo.ui.demo;

import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.xiaoli.library.db.DaoUtils;
import com.xiaoli.library.utils.PromptUtils;
import com.xiaolidemo.Config;
import com.xiaolidemo.R;
import com.xiaolidemo.game.PuzzleView;
import com.xiaolidemo.model.Branch;
import com.xiaolidemo.ui.CoreActivity;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 拼图游戏
 */
public class PuzzleActivity extends CoreActivity {


    private int index=0;//选择位图

    @Bind(R.id.mPuzzleView)
    PuzzleView mPuzzleView;

    @Bind(R.id.mBtnPreviou)
    Button mBtnPreviou;

    @Bind(R.id.mBtnNext)
    Button mBtnNext;

    @Bind(R.id.mIvPreview)
    ImageView mIvPreview;

    @OnClick(R.id.mBtnPreviou)
    public void mBtnPreviou(){
        index --;
        if(index<0){
            index = Config.puzzleImgList.size()-1;
        }
        mPuzzleView.setmBitmap(Config.puzzleImgList.get(index));
        mIvPreview.setBackgroundDrawable(new BitmapDrawable(Config.puzzleImgList.get(index)));
    }

    @OnClick(R.id.mBtnNext)
    public void mBtnNext(){
        index++;
        if(index>=Config.puzzleImgList.size()){
            index = 0;
        }
        mPuzzleView.setmBitmap(Config.puzzleImgList.get(index));
        mIvPreview.setBackgroundDrawable(new BitmapDrawable(Config.puzzleImgList.get(index)));
        Branch branch = new Branch();
        branch.setBranchCode("33333sa");
        branch.setBranchID(334);
        branch.setBranchName("dddd");
        Map map = DaoUtils.voToMap(branch);
        Log.e("map",map.toString());
    }

    @OnClick(R.id.mBtnSelect)
    public void mBtnSelect(){

    }


    @Override
    protected int getLayoutResId() {
        return R.layout.act_puzzle;
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPuzzleView.setOnFinishListener(new PuzzleView.OnFinishListener() {
            @Override
            public void onFinish() {
                PromptUtils.showMessage("恭喜你完成拼图");
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
//        Config.puzzleImgList.add(((BitmapDrawable) ContextCompat.getDrawable(this,R.drawable.puzzle_default)).getBitmap());
//        Config.puzzleImgList.add(((BitmapDrawable)ContextCompat.getDrawable(this,R.drawable.p1)).getBitmap());
//        Config.puzzleImgList.add(((BitmapDrawable)ContextCompat.getDrawable(this,R.drawable.p2)).getBitmap());
//        Config.puzzleImgList.add(((BitmapDrawable)ContextCompat.getDrawable(this,R.drawable.p3)).getBitmap());
//        Config.puzzleImgList.add(((BitmapDrawable)ContextCompat.getDrawable(this,R.drawable.p4)).getBitmap());
//        Config.puzzleImgList.add(((BitmapDrawable)ContextCompat.getDrawable(this,R.drawable.p5)).getBitmap());
//        Config.puzzleImgList.add(((BitmapDrawable)ContextCompat.getDrawable(this,R.drawable.p6)).getBitmap());
//        mIvPreview.setBackgroundDrawable(new BitmapDrawable(Config.puzzleImgList.get(index)));
        mPuzzleView.setLevel(4);
//        Bitmap mBitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.guide4)).getBitmap();
//        mPuzzleView.setmBitmap(mBitmap);
    }
}
