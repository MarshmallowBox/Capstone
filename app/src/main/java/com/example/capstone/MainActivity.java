package com.example.capstone;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    private SearchView searchView;
    private MenuItem mSearch;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //키해시값 구하기 -> Key Hash Value : ~~~~
//        GetKeyHashValue.showKeyHash(this);


        //카카오맵
//        Maps maps = new Maps(MainActivity.this);


        //앱바(툴바) 생성부분인데 밑에 이벤트와같이 UI클래스에 같이 합칠수있는지 시도해보기
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.main);
        this.setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_black_18dp); //뒤로가기 버튼 이미지 지정

        //이거 UI로 옮기는거 찾으면 더깔끔쓰~
        //하단바클릭이벤트를 위한 프레그먼트 생성
        fragmentManager = getSupportFragmentManager();


        //UI
        UI ui = new UI(this);
        ui.createCategory();
        ui.createNav_Bottom();
        ui.createNav_Drawer();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 왼쪽 상단 버튼 눌렀을 때
                Toast.makeText(MainActivity.this, "햄버거", Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.search_button:
                Toast.makeText(MainActivity.this, item.getItemId() + ": search_button", Toast.LENGTH_SHORT).show();

                return true;

            default:
                Toast.makeText(MainActivity.this, item + ":default", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_item, menu);
        mSearch = menu.findItem(R.id.search_button);
        searchView = (SearchView) mSearch.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                Toast.makeText(MainActivity.this, "검색결과 : " + s, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //입력할때마다 이벤트
                //여기다 자동완성 넣으면 갸꿀
                return false;
            }
        });
//메뉴 아이콘 클릭했을 시 확장, 취소했을 시 축소
        mSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                //확장시
                Toast.makeText(MainActivity.this, "확장", Toast.LENGTH_SHORT).show();
                searchView.setIconified(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //축소시
                Toast.makeText(MainActivity.this, "축소", Toast.LENGTH_SHORT).show();

                searchView.setIconified(true);
                searchView.clearFocus();
                return true;
            }
        });
        return true;
    }

}
