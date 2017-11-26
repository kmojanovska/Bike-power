package finki.das.bikepower_v1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import finki.das.bikepower_v1.adapter.SlidingMenuAdapter;
import finki.das.bikepower_v1.fragment.Fragment1;
import finki.das.bikepower_v1.fragment.Fragment2;
import finki.das.bikepower_v1.fragment.Fragment3;
import finki.das.bikepower_v1.fragment.Fragment4;
import finki.das.bikepower_v1.model.ItemSlideMenu;

/**
 * Created by Kristina on 24.11.2017.
 */

public class MainActivity extends ActionBarActivity {

    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
//    private RelativeLayout mainContent;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        listViewSliding = (ListView)findViewById(R.id.lv_sliding_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
  //      mainContent = (RelativeLayout) findViewById(R.id.main_content);
        listSliding = new ArrayList<ItemSlideMenu>();
        listSliding.add(new ItemSlideMenu(R.mipmap.ic_launcher, "Bike Power Motor"));
        listSliding.add(new ItemSlideMenu(R.drawable.home, "Home"));
        listSliding.add(new ItemSlideMenu(R.drawable.history, "History"));
        listSliding.add(new ItemSlideMenu(R.drawable.settings, "Settings"));
        listSliding.add(new ItemSlideMenu(R.drawable.about, "About"));

        adapter = new SlidingMenuAdapter(this, listSliding);
        listViewSliding.setAdapter(adapter);

        //display  close
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //set title
        setTitle(listSliding.get(0).getTitle());
        //item selected
        listViewSliding.setItemChecked(0,true);
        //close menu
        drawerLayout.closeDrawer(listViewSliding);
        //nesto so ne e 1 2 ili 3 znaci e 1 prvo da pocni so fragment 1
        replaceFragment(0);

        //handle on item clicks
        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //setTitle
                setTitle(listSliding.get(i).getTitle());
                //item selected
                listViewSliding.setItemChecked(i,true);
                //pusti go ovaj fragment
                replaceFragment(i);
                //close menu
                drawerLayout.closeDrawer(listViewSliding);


            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.drawer_opened_kristina, R.string.drawer_closed_kristina){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    //create method repace fragment
    private void replaceFragment(int pos){
        Fragment fragment = null;
        switch (pos){
            case 1:
                fragment = new Fragment1();
                break;
            case 2:
                fragment = new Fragment2();
                break;
            case 3:
                fragment = new Fragment3();
                break;
            case 4:
                fragment = new Fragment4();
                break;
            default:
                fragment = new Fragment1();
                break;
        }

        if(null!=fragment){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content,fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
