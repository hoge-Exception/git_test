package com.example.tableorder.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import com.example.tableorder.service.MenuService;

public class MenuControllerTest {

    private MenuController controller;
    private Model model;
    private MenuService menuService;

    /*
	@BeforeAll
	static void initAll() throws Exception {
		System.out.println("@BeforeAll：テスト開始前に1回だけ実行");
	}
	*/
	
	@BeforeEach
	void setUp() {
		System.out.println("@BeforeEach：テストメソッド前に毎回テスト対象クラスをリセット");
		this.menuService = mock(MenuService.class);
		this.controller = new MenuController(menuService);
	}
	
	/*
	@AfterEach
	void tearDown() {
		System.out.println("@AfterEach：テストメソッド後に毎回呼ばれる");
	}
	
	@AfterAll
	static void tearDownAll() {
		System.out.println("@AfterAll：テスト終了後に1回だけ実行");
	}
	*/
	
	@Test
	@DisplayName("showMenuManagementメソッドのテスト")
	void testShowMenuManagement() {
		// 準備: MenuService.getAllMenus() が空のリストを返すようモックの振る舞いを設定
		when(menuService.getAllMenus()).thenReturn(Collections.emptyList());
		// 実行: showMenuManagementメソッドを呼び出し、戻り値（ビュー名）を取得
		this.model = mock(Model.class);
		String view = controller.showMenuManagement(model);
		// 検証: 戻り値が期待通り "menu_management" であることを確認
		assertEquals("menu_management", view);
		// 検証: Modelに "menus" 属性として空のリストが追加されたことを確認
		verify(model).addAttribute("menus", Collections.emptyList());
		// 追加検証（オプション）: menuService.getAllMenus() が1回だけ呼ばれたことを確認
	    verify(menuService, times(1)).getAllMenus();
	}
}
