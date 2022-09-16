package com.mycom.word;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD{
	ArrayList<Word> list;
	Scanner s = new Scanner(System.in);
	final String fname = "Dictionary.txt";
	WordCRUD(Scanner s)
	{
		list = new ArrayList<>();
		this.s = s;
	}
	@Override
	public Object add() {
		System.out.print("\n=> 난이도(1, 2, 3) & 새 단어 입력 : ");
		int level = s.nextInt();
		String word = s.nextLine();
		
		System.out.print("뜻 입력 : ");
		String meaning = s.nextLine();
		
		return new Word(0, level, word, meaning);
	}
	
	public void addItem(){
		Word one = (Word)add(); // newWord() 하고 one.add()가능할 듯.
		list.add(one);
		System.out.println("\n새 단어가 단어장에 추가되었습니다 !!! \n");
	}
	@Override
	public int update(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void selectOne(int id) {
		// TODO Auto-generated method stub
		
	}
	
	public void listAll() {
		System.out.println("\n-----------------------------");
		for(int i = 0; i < list.size(); i++)
		{
			System.out.print((i+1) + " ");
			System.out.println(list.get(i).toString()); // arraylist 안에 해당하는 순서를 꺼내옴 0 번째 ~ 3번째
		}
		System.out.println("-----------------------------\n");
	}
	
	public ArrayList<Integer> listAll(String keyword) {
		ArrayList<Integer> idlist = new ArrayList<>();  
		int j = 0;
		System.out.println("\n-----------------------------");
		for(int i = 0; i < list.size(); i++)
		{
			String word = list.get(i).getWord();
			if(!word.contains(keyword))continue;
			System.out.print((j+1) + " ");
			System.out.println(list.get(i).toString()); // arraylist 안에 해당하는 순서를 꺼내옴 0 번째 ~ 3번째
			idlist.add(i);
			j++;	
		}
		
		if(idlist.isEmpty())
		{
			System.out.println("검색한 단어가 존재하지 않습니다.");
			System.out.println("다시 입력해 주세요.");
			System.out.println("-----------------------------\n");
			updateItem();
		}
		
		System.out.println("-----------------------------\n");
		

		return idlist;
	}
	
	public void listAll(int level) { 
		int j = 0;
		System.out.println("\n-----------------------------");
		for(int i = 0; i < list.size(); i++)
		{
			int ilevel = list.get(i).getLevel();
			if(ilevel != level)continue;
			System.out.print((j+1) + " ");
			System.out.println(list.get(i).toString()); // arraylist 안에 해당하는 순서를 꺼내옴 0 번째 ~ 3번째
			j++;	
		}
	}
	
	public void updateItem(
	{
		System.out.print("=> 수정할 단어 검색 : (메뉴로 돌아가려면 0을 입력하세요.)");
		String keyword = s.next();
		if(keyword.equals("0")) return;
		ArrayList<Integer> idlist = this.listAll(keyword);
		System.out.print("=> 수정할 번호 선택 : ");
		int id = s.nextInt();
		s.nextLine(); // 위에 엔터가 여기로 들어감.
		
		System.out.print("=> 뜻 입력 : ");
		String meaning = s.nextLine();
		Word word = list.get(idlist.get(id-1)); // Word word 해서 word의 배열 중에서 수정하고자 하는 해당 배열을 수정해준다.
		word.setMeanig(meaning);
		System.out.println("단어가 수정되었습니다. ");
	}
	public void deleteItem() 
	{
		System.out.print("=> 삭제할 단어 검색 : ");
		String keyword = s.next();
		ArrayList<Integer> idlist = this.listAll(keyword);
		System.out.print("=> 삭제할 번호 선택 : ");
		int id = s.nextInt();
		s.nextLine(); // 위에 엔터가 여기로 들어감.
		
		System.out.print("=> 정말로 삭제하실래요?(Y/N) ");
		String answer = s.nextLine();
		if(answer.equalsIgnoreCase("y"))
		{
			list.remove((int)idlist.get(id-1)); // Integer값으로 리턴받기 때문에 int형으로 바꿔서 정확한 위치에서 삭제 할 수 있도록 한다.
			System.out.println("단어가 삭제되었습니다. ");
		}
		else
		{
			System.out.println("취소되었습니다. ");
		}

	}
	
	public void loadFile()
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(fname));
			String line;
			int count = 0;
			
			while(true)
			{
				line = br.readLine();
				if(line == null) break;
				String data[] = line.split("\\|");
				int level = Integer.parseInt(data[0]);
				String word = data[1];
				String meaning = data[2];
				list.add(new Word(0, level, word, meaning));
				count ++;
			}
			br.close();
			System.out.println("==> " + count + "개 로딩 완료!!!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void saveFile() {
		try {
			PrintWriter pr = new PrintWriter(new FileWriter(fname));
			for(Word one : list)
			{
				pr.write(one.toFileString() + "\n");
			}
			pr.close();
			System.out.println("==> 데이터 저장 완료 !!!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void searchLevel() {
		System.out.print("=> 원하는 레벨은? (1~3) ");
		int level = s.nextInt();
		listAll(level);
		
	}
	public void searchWord() {
		System.out.print("=> 원하는 단어는? ");
		String keyword = s.next();
		listAll(keyword);
	}

}
