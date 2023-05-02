package sample03;

import java.sql.SQLException;
import java.util.List;

import util.KeyboardReader;

public class ProductApp {

	ProductDao dao = new ProductDao();
	KeyboardReader reader = new KeyboardReader();
	
	public void menu() {
		System.out.println("### 상품관리 프로그램 ###");
		
		System.out.println("--------------------------------------------------");
		System.out.println("1.전체조회 2.상세조회 3.검색 4.저장 5.삭제 0.종료");
		System.out.println("--------------------------------------------------");
		System.out.println();
		
		System.out.print("### 메뉴선택: ");
		int menuNo = reader.readInt();
		System.out.println();
		
		try {
			if (menuNo == 1) {
				전체조회();
			} else if (menuNo == 2) {
				상세조회();
			} else if (menuNo == 3) {
				검색();
			} else if (menuNo == 4) {
				저장();
			} else if (menuNo == 5) {
				삭제();
			} else if (menuNo == 0) {
				종료();
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		menu();
	}
	
	
	private void 전체조회() throws SQLException {
		System.out.println("<< 전체 상품정보 조회 >>");
		System.out.println("### 전체 상품정보를 확인하세요.");
		
		List<Product> products = dao.getAllProduct();
		
		if (products.isEmpty()) {
			System.out.println("### 상품정보가 존재하지 않습니다.");
		} else {
			System.out.println("---------------------------------------------------");
			System.out.println("상품번호\t이름\t브랜드\t가격\t할인가격");
			System.out.println("---------------------------------------------------");
			for (Product product : products) {
				System.out.print(product.getNo() + "\t");
				System.out.print(product.getName() + "\t");
				System.out.print(product.getMaker() + "\t");
				System.out.print(product.getPrice() + "\t");
				System.out.println(product.getDiscountPrice() + " \t");
			}
			System.out.println("---------------------------------------------------");
		}
	} 
	
	
	private void 상세조회() throws SQLException {
		System.out.println("<< 상품 상세정보 조회 >>");
		System.out.println("### 조회할 상품정보를 입력하세요.");
		
		System.out.print("### 상품번호: ");
		int no = reader.readInt();
		
		Product product = dao.getProductByProductNo(no);
		if (product == null) {
			System.out.println("### [" +no+ "]번의 상품정보가 존재하지 않습니다.");
		} else {
			System.out.println("---------------------------------------------------");
			System.out.println("["+product.getNo()+"]번의 삼품정보");
			System.out.println("---------------------------------------------------");
			System.out.println("상품번호: " +product.getNo());
			System.out.println("상품이름: " +product.getName());
			System.out.println("제조회사: " +product.getMaker());
			System.out.println("상품가격: " +product.getPrice());
			System.out.println("할인비율: " +product.getDiscountRate());
			System.out.println("할인가격: " +product.getDiscountPrice());
			System.out.println("재고수량: " +product.getStock()); 
			System.out.println("등록일자: " +product.getCreateDate());
			System.out.println("---------------------------------------------------");
		}
	}
	
	
	private void 검색() throws SQLException {
		System.out.println("<< 상품 정보 검색 >>");
		System.out.println("### 가격을 입려해서 상품을 검색하세요.");
		
		System.out.print("최소가격: ");
		int minPrice = reader.readInt();
		System.out.print("최대가격: ");
		int maxPrice = reader.readInt();
		System.out.println();
		
		List<Product> products = dao.getProductByPrice(minPrice, maxPrice);
		if (products.isEmpty()) {
			System.out.println("### 상품정보가 존재하지 않습니다.");
		} else {
			System.out.println("가격 [" +minPrice+"] ~ ["+maxPrice+"]");
			System.out.println("---------------------------------------------------");
			System.out.println("상품번호\t상품이름\t가격\t할인가격");
			System.out.println("---------------------------------------------------");
			for (Product product : products) {
				System.out.print(product.getNo() + "\t");
				System.out.print(product.getName() + "\t");
				System.out.print(product.getPrice() + "\t");
				System.out.println(product.getDiscountPrice() + "\t");
			}
			System.out.println("---------------------------------------------------");
		}
	}
	
	
	private void 저장() throws SQLException {
		System.out.println("<< 상품정보 저장 >>");
		System.out.println("### 상품번호, 이름, 브랜드, 가격, 할인율, 수량을 입력해서 상품정보를 저장하세요.");
		
		System.out.print("### 이름입력: ");
		String name = reader.readString();
		System.out.print("### 브랜드입력: ");
		String maker = reader.readString();
		System.out.print("### 가격입력: ");
		int price = reader.readInt();
		System.out.print("### 할인율입력: ");
		double discountRate = reader.readDouble();
		System.out.print("### 수량입력: ");
		int stock = reader.readInt();
		
		Product product = new Product();
		product.setName(name);
		product.setMaker(maker);
		product.setPrice(price);
		product.setDiscountRate(discountRate);
		product.setStock(stock);
		
		dao.insertProduct(product);
		
		System.out.println("### 신규 상품정보가 저장되었습니다.");
	}
	
	
	private void 삭제() throws SQLException {
		System.out.println("<< 상품정보 삭제 >>");
		System.out.println("### 삭제할 상품번호를 입력하세요.");
		
		System.out.print("### 상품번호: ");
		int no = reader.readInt();
		
		dao.deleteProductByNo(no); 
		
		System.out.println("### 상품정보가 삭제 되었습니다.");
	}
	
	
	private void 종료() {
		System.out.println("<< 프로그램 종료 >>");
		System.out.println("### 프로그램을 종료합니다.");
		System.exit(0);
	}
 	
	public static void main(String[] args) {
		new ProductApp().menu();
	}
}
