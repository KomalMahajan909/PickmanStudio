package com.itechvision.ecrobo.pickman.Chatman.Ship;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.itechvision.ecrobo.pickman.Adapter.ShippingSpecification.BoxSelectAdapter;
import com.itechvision.ecrobo.pickman.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoxListActivity extends AppCompatActivity implements BoxSelectAdapter.ItemClickListener  {

    @BindView(R.id.rvNumbers)
    RecyclerView rvNumbers;


    BoxSelectAdapter adapter;
    BoxSelectAdapter.ItemClickListener itemClickListener;

    String size[]={"70", "90", "130","170","180","190", "220","240","260","270","280","290","300"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.box_button_layout);



        ButterKnife.bind(this);


     /*   int numberOfColumns = 3;
        rvNumbers.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new BoxSelectAdapter(this,ShippingSpecificationActivity. arrBox, itemClickListener);

        rvNumbers.setAdapter(adapter);*/
    }



    @Override
    public void onItemClick(int position) {

    }
}