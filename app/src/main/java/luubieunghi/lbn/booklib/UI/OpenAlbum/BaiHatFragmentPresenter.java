package luubieunghi.lbn.booklib.UI.OpenAlbum;

import android.content.Context;

import java.util.ArrayList;

import luubieunghi.lbn.booklib.Model.BaiHat;

public class BaiHatFragmentPresenter implements BaiHatFragmentContract.IBaiHatFragmentPresenter {
    private BaiHatFragmentContract.IBaiHatFragmentView view;
    private Context context;
    private ArrayList<BaiHat> dsBaiHat;
    public BaiHatFragmentPresenter(Context context, BaiHatFragmentContract.IBaiHatFragmentView view)
    {
        this.context=context;
        this.view=view;
        dsBaiHat=new ArrayList<>();
        for(int i=0;i<50;i++){
            dsBaiHat.add(new BaiHat("Bài hát "+i,"Ca sĩ "+i,i,i));
        }
    }

    @Override
    public void updateListView() {
        view.updateListView(dsBaiHat);
    }
}
