package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

import entity.Sale;

public class Program {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		Locale.setDefault(Locale.US);
		
		System.out.print("Entre o caminho do arquivo: ");
		String filePath = sc.next();
		
		List<Sale> salesList = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line = br.readLine();
			
			while (line != null) {
				String[] fields = line.split(",");
				salesList.add(new Sale( Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}
			
			Comparator<Sale> comp = (s1, s2) -> s1.averagePrice().compareTo(s2.averagePrice());
			
			Stream<Sale> salesPerAvgDesc = 
					salesList.stream()
					.filter(p -> p.getYear() == 2016)
					.sorted(comp.reversed())
					.limit(5);
			
			System.out.println();
			
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			salesPerAvgDesc.forEach(System.out::println);
			
			Double loganSalesMonths1and7 = 
					salesList.stream()
					.filter(p -> p.getSeller().equals("Logan") && (p.getMonth() == 1 || p.getMonth() == 7))
					.map(p -> p.getTotal())
					.reduce(0.0, (x, y) -> x+y);
			
			System.out.println();
			
			System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = "  + String.format("%.2f", loganSalesMonths1and7));

			System.out.println();
		}
		catch(IOException e) {
			System.out.println("Error: " + filePath + " (O sistema não pode encontrar o arquivo especificado)");
		}
		
		sc.close();
	}

}
