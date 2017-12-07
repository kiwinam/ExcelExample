package com.example.charlie.excelexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View
        ;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> mItems = new ArrayList<>();

    private RecyclerView itemRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        itemRv = findViewById(R.id.mainRv);
        initData();
        setRv();
        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExcel();
            }
        });
    }

    private void initData(){
        mItems.add(new Item("키위남",25));
        mItems.add(new Item("강감찬",30));
        mItems.add(new Item("이순신",35));
        mItems.add(new Item("홍길동",40));
        mItems.add(new Item("이성계",45));
        mItems.add(new Item("아무개",50));
    }

    private void setRv(){
        ItemAdapter itemAdapter = new ItemAdapter(mItems);
        itemRv.setAdapter(itemAdapter);
        itemRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void saveExcel(){
        Workbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet(); // 새로운 시트 생성

        Row row = sheet.createRow(0); // 새로운 행 생성
        Cell cell;

        cell = row.createCell(0); // 1번 셀 생성
        cell.setCellValue("이름"); // 1번 셀 값 입력

        cell = row.createCell(1); // 2번 셀 생성
        cell.setCellValue("나이"); // 2번 셀 값 입

        for(int i = 0; i < mItems.size() ; i++){ // 데이터 엑셀에 입력
            row = sheet.createRow(i+1);
            cell = row.createCell(0);
            cell.setCellValue(mItems.get(i).getName());
            cell = row.createCell(1);
            cell.setCellValue(mItems.get(i).getAge());
        }

        File xlsFile = new File(getExternalFilesDir(null),"test.xls");
        try{
            FileOutputStream os = new FileOutputStream(xlsFile);
            workbook.write(os); // 외부 저장소에 엑셀 파일 생성
        }catch (IOException e){
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),xlsFile.getAbsolutePath()+"에 저장되었습니다",Toast.LENGTH_SHORT).show();

        Uri path = Uri.fromFile(xlsFile);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/excel");
        shareIntent.putExtra(Intent.EXTRA_STREAM,path);
        startActivity(Intent.createChooser(shareIntent,"엑셀 내보내기"));
    }
}
