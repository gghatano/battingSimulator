import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern; // 正規表現

class GameNumber{ // number of games
  public int getGameNumber(){
    return 100;
  }
}

class Coordinate{ //選手名に個人成績をまとめる
	double  avg,hit4,p_hit1,p_hit2,p_hit3,p_hit4,p_swing,p_other;
	double longhit;
	String battername;
	
	Coordinate(String batternamea,double avga,double hit1a,double hit2a,double hit3a,double hit4a,double atbata,double swinga,double othera){
		
		battername = batternamea;
		avg = avga;
		hit4 = hit4a;
		p_hit1 = hit1a / atbata; // 単打率
		p_hit2 = hit2a / atbata; // 二塁打率
		p_hit3 = hit3a / atbata; // 三塁打率
		p_hit4 = hit4a / atbata; // ホームラン率
		p_swing= swinga/ atbata; // 三振率
		p_other= othera/ atbata; // その他出塁率
		longhit = (hit1a*1+hit2a*2+hit3a*3+hit4a*4) / atbata; // 長打率
	}
}

class Offence{ // 打者名とベース状態を入力すると、打席結果を計算する
	Coordinate batter;
	int first, second, third; // ベース状態
	int score;              // 得点
	int outcount;           // アウトカウント
	int hitting;			// １試合の累帥ミqット数
	int fourball;			// １試合の四死球数
	
	Offence(Coordinate batter1,int b11,int b21,int b31,int outcount1,int score1, int hittings,int fourball1){ // データの代入
		batter = batter1;
		first = b11 ; second = b21 ; third = b31;
		outcount = outcount1;
		score = score1;
		hitting = hittings;
		fourball = fourball1;
		attack();
	}
	
	void attack(){ // 打撃結果の分岐
		double random = Math.random();
		if(random<=batter.p_hit1){ // 単打
			base(1);
		}
		else if(random<=batter.p_hit1+batter.p_hit2){ // 二塁打
			base(2);
		}
		else if(random<=batter.p_hit1+batter.p_hit2+batter.p_hit3){// 三塁打
			base(3);
		}
		else if(random<=batter.p_hit1+batter.p_hit2+batter.p_hit3+batter.p_hit4){// ホームラン
			base(4);
		}
		else if(random<=batter.p_hit1+batter.p_hit2+batter.p_hit3+batter.p_hit4+batter.p_swing){// 三振
			base(0);
		}
		
		else if(random<=batter.p_hit1+batter.p_hit2+batter.p_hit3+batter.p_hit4+batter.p_swing+batter.p_other){// その他出塁
			base(5);
		}
		
		else{ // ヒッティングアウト
			base(6);
		}
	}
	
	void base(int result){ // ベース状態の変化
		int getscore=0;
		double timely;
			if(batter.longhit>0.5)	timely = 0.8;
			else					timely = 0.5;
		double timelyhit = Math.random(); // タイムリーヒットになる確率(適当)
	
		switch(result){
			case 0:// 三振
				//System.out.println("swingout!!");
				outcount +=1;
			break;
			
			case 1: // 単打
				//System.out.println("hit!!");
				hitting += 1;
				if(timelyhit<timely){
					score += third + second;
					third=0; second=first; first=1;
				}
				else{
					score += third;
					third=second; second=first; first=1;
					}
			break;
			
			case 2:// 二塁打
				//System.out.println("double!!");
				hitting += 1;
				score += second+third;
				third = first; second=1; first=0;
			break;
			
			case 3:// 三塁打
				//System.out.println("triple!!");
				hitting += 1;
				score += third+second+first;
				third=1;second=0;first=0;
			break;
			
			case 4:// 本塁打
				//System.out.println("homerun!!");
				hitting += 1;
				score += first + second + third + 1;
				first = 0; second = 0; first = 0;
			break;
			
			case 5:// その他出塁
				//System.out.println("fourball!!");
				if(first==1){
					if(second==1){
						score += third;
						third = second; second = first; 
					}
				}
				else {
					second = first;
				}
				first = 1;
				fourball +=1;
			break;
			
			case 6:// ヒッティングアウト
				//System.out.println("out!!");
				if(first==1&&second==0){ // ダブルプレー判定
                             // これは適当
					if(Math.random()<0.700){
						outcount+=2;
						first = 0 ; second = 0;
					}
					else{
						outcount += 1;
						first = 0 ; second = 1;
					}
				}
				
				else{ // 進塁打
          // これは適当
					if(Math.random()<0.600) outcount+=1; //進塁打失敗
					else{
						outcount += 1;
						score += third;
						third = second; second = first; first = 0;
					}
				}
			break;
		}
		//System.out.println(first+" "+second+" "+third + " " + score +"\n");
	}
}


class Batting{ // 打撃能力の計算
    GameNumber gamenumber = new GameNumber();
		int game_number = gamenumber.getGameNumber(); // 試行回数
		String battername ; 
		String result;
		MyTextArea area;
	
		Coordinate batter[] = new Coordinate[10];
	
		int outcount = 0,score=0,b1=0,b2=0,b3=0;
		int inning = 1;
		int hitting = 0;
		int scoreall = 0;
	
		double scoreavg = 0; // 平均得点
		double hittingavg = 0;
		double dasekisuuavg = 0;
		double fourballavg = 0;
		int dasekisuu=0;
		int fourball = 0;
	
		Batting(Coordinate[] batter1){
			batter = batter1;
		}
	
		void run(){
			
			for(int games=1;games<=game_number;games++){
				while(inning<=9){
					
					int i = 1;
					
					//System.out.println("inning:"+inning+"\n\n");
					
					while(outcount<3){
						Offence batternow = new Offence(batter[i],b1,b2,b3,outcount,score,hitting,fourball) ;
						dasekisuu+=1;
						outcount = batternow.outcount;
						score = batternow.score;
						b1 = batternow.first;
						b2 = batternow.second;
						b3 = batternow.third;
						hitting = batternow.hitting;
						fourball = batternow.fourball;
						//System.out.println(b1+" "+b2+" "+b3 + " " + score);
						i+=1;
						if(i>9) i=1;
					}
					
					inning+=1;
					b1=0;b2=0;b3=0;
					outcount = 0;
				}
				inning = 0;
				
				scoreavg = ( (games-1)*scoreavg + score ) / (double)games;
				hittingavg = ( (games-1)*hittingavg + hitting ) / (double)games;
				dasekisuuavg = ( (games-1)*dasekisuuavg + dasekisuu ) / (double)games;
				fourballavg = ( (games-1)*fourballavg + fourball ) / (double)games;
				
				score = 0;
				hitting =0;
				dasekisuu = 0;
				fourball = 0;
			}
			
			scoreavg = ( (int)(scoreavg*10000)) / 10000.0;
			hittingavg = ( (int)(hittingavg*1000)) / 1000.0;
			dasekisuuavg = ( (int)(dasekisuuavg*1000)) / 1000.0;
			fourballavg = ( (int)(fourballavg*1000)) / 1000.0;
			int calclateavg =  (int)( hittingavg / (dasekisuuavg-fourballavg) * 1000);			
			
			
			result = "   --- 計算結果 ---\n\n";
			
			result += "・試行回数 : "+Integer.toString(game_number)+"試合\n\n1試合平均得点 : "+Double.toString(scoreavg)+"\n";
			result += "・1試合平泣ヒット数 : "+Double.toString( hittingavg) +"\n";
			result += "・1試合平均打席数 : "+ Double.toString( dasekisuuavg) + "\n";
			result += "・1試合平均四死球数 :"+Double.toString(fourballavg)+"\n";
			result += "・チーム打率 :"+Integer.toString(calclateavg) + "\n\n";
		}
}


class Caliculate{
	Coordinate[] batter = new Coordinate[10]; // 初期打線
	Coordinate[] batter_shuffle = new Coordinate[10];//入れ替え打線
	Coordinate[] batter_opt = new Coordinate[10]; // 最適打線
	
	Coordinate batter_temp;
	
	Data data[] = new Data[10];
	String member[] = new String[9];
	double score1,score2,scoremax;
	Batting team1,team2,teamtemp;
	String result1="",result2="",resulttemp;
	int change = 0;
	
	
	int changelimit = 1000; // 累積打順変更回数とその上限
	
	
	int nullpo = 0; // 強制終了カウント
	
	Caliculate(String[] member1){
		member = member1;
		run();
		shuffle();
	}
	// 打者データ要素：名前、打率、出塁率、ナ数、二塁打数、三塁打数、本塁打数、打席数、打数、三振数
	double avg,obp,hit,hit1,hit2,hit3,hit4,atbat,bat,swing,other;
	
	void run(){
		for(int i=1;i<=9;i++){
			data[i]  = new Data(member[i]);// 打者の名前を入力すると、成推━渡される。
			avg = data[i].avg/1000.0 ; obp = data[i].obp/1000.0;
			hit = data[i].hit;  hit2 = data[i].hit2 ; hit3 = data[i].hit3 ; hit4 = data[i].hit4;
			atbat = data[i].atbat ; bat = data[i].bat ; swing = data[i].swing ;
			other = atbat - bat;
			hit1  = hit - hit2 - hit3 - hit4; 
			//　名前　打率　単打数　二塁打数　三塁打数　本塁打数　打席数　三振数　その他出塁
			batter[i] = new Coordinate(member[i],avg,hit1,hit2,hit3,hit4,atbat,swing,other); // 情報をまとめる
		}
		batter_shuffle = batter;
		
		team1 = new Batting(batter);
		team1.run();
		score1 = team1.scoreavg;
		result1 += "\n\n   --- 入力した打線 ---\n";
		for(int j=1;j<=9;j++){
			//System.out.println(j + "番 " + batter[j].battername);
			//System.out.println("打率：."+(int)(batter[j].avg*1000)+" 本塁打："+(int)batter[j].hit4+"\n");
			
			result1 += "\n"+Integer.toString(j) + "番 : "+ batter[j].battername;
			result1 += "  打率:" +Integer.toString( (int)(batter[j].avg*1000))+" 本塁打："+Integer.toString( (int)batter[j].hit4) +"\n";
		}
	}
	
	void shuffle(){
		while(change < changelimit){
			int random1 = (int)(Math.random()*9+1),random2 = (int)(Math.random()*9+1);
			
			if(random1-random2> 0.001){// ランダムが同じじゃなか・スら、打順入れ替え続行
				
				//System.out.println("入れ替え "+ random1+" と "+random2);
				
				change += 1; // 変更回数に+1する。
				batter_temp = batter_shuffle[random1];
				batter_shuffle[random1] = batter_shuffle[random2];
				batter_shuffle[random2]= batter_temp;           //入れ替え
			
				team2 = new Batting(batter_shuffle);
				team2.run();
				score2 = team2.scoreavg; // 平均得点の算出
				
				if(score2>score1){ // 入れ替えた結果、得点効率が純I・ス場合
					batter_opt = batter_shuffle; // 結果をoptに取・トおく
					resulttemp = team2.result;
				}
			}
		}
		Print();
	}
	
	void Print(){
		try{
			result1 += "\n\n"+team1.result;
			//System.out.println(result1+"\n\n");
			//System.out.println("最適化した打順\n\n");
			result2 += "   --- 最適化した打順 ---\n\n";
			for(int j=1;j<=9;j++){
				//System.out.println(j + "番 : " + batter_opt[j].battername );
				//System.out.println("打率："+(int)(batter_opt[j].avg*1000)+" 本塁打："+(int)batter_opt[j].hit4+"\n\n");
				result2 += Integer.toString(j) + "番　"+ batter_opt[j].battername;
				result2 += "  打率：" + Integer.toString((int)(batter_opt[j].avg*1000) ) +" 本塁打："+Integer.toString( (int)batter_opt[j].hit4 ) +"\n\n";
			}
			result2 += "\n\n" + resulttemp;
			//System.out.println(result2);
		}
		catch(NullPointerException e){
			result1 = "";
			result2 = "";
			nullpo += 1;
			if(nullpo>10){
				System.out.println("エラー\n");
				System.exit(0); // 強制終了
			}
			run();
			shuffle();
		}
	}
}

class MyTextField extends TextField{
    MyTextField(BattingSimulator3 br,String str){
        super("巨人",25);
        br.add(this);
    }
}

class MyTextArea extends TextArea{
    MyTextArea(BattingSimulator3 br){
        super("\n選手名かチーム名を入力してGOボタンを押してください。\n\n1番から9番まで入力したら、STARTボタンを押すと計算が開始されます。\n\nスターティングメンバーの入力\n\n",30,60,TextArea.SCROLLBARS_BOTH);//初期化
        setEditable(false); //編集不可
        setFont(new Font("Helvetica",Font.PLAIN,12));//フォントの設定
        br.add(this);//ブラウザ上に配置
    }
}

abstract class MyButton extends Button implements ActionListener{

	final BattingSimulator3 browser;    

	MyButton(BattingSimulator3 br,String name){
		super(name);
		browser = br;
		browser.add(this); //ブラウザ上に配置
		addActionListener(this);
    }
    abstract public void actionPerformed(ActionEvent e);
    //「○○する」の具体的内容を定義する
}

class MyGoButton extends MyButton{
    MyGoButton(BattingSimulator3 br){
        super(br,"Go!");
    }
    public void actionPerformed(ActionEvent e){
        browser.go();
        //Goボタンが押されたら、BattingSimulator3のgo()
    }
}

class MyQuitButton extends MyButton{
    MyQuitButton(BattingSimulator3 br){
        super(br,"Quit!");      
    }
    public void actionPerformed(ActionEvent e){
        browser.quit();
        //Quitボタンが押されたら、BattingSimulator3全体をquit
    }
}

class MyStartButton extends MyButton{
	MyStartButton(BattingSimulator3 br){
		super(br,"Start!");
	}
	public void actionPerformed(ActionEvent e){	
		browser.calc();
    }
}

public class BattingSimulator3 extends Applet{ 
    //提出時は、Appletとして提出。
    MyTextArea myTextArea; 
    MyTextField myTextField1;
	String page;
    StringBuffer pageStr;    
	String battername;
	int number = 0;
	String[] member = new String[100];
	String[] startingmember = new String[10];
	
    void quit(){
        System.exit(0);
    }
    void go(){
    	battername = myTextField1.getText();
    	
    	if(battername.equals("阪神")||battername.equals("阪神タイガース")){
    		member[1]="マートン";
    		member[2]="平野恵一";
    		member[3]="鳥谷敬";
    		member[4]="新井貴浩";
    		member[5]="ブラゼル";
    		member[6]="金本知憲";
    		member[7]="俊介";
    		member[8]="淀諌助";
    		member[9]="城島健司";
    		
    		for(int i=1;i<=9;i++){
    			myTextArea.append(i + "番 : " + member[i] + "\n");
    		};
    		number=9;
    	}
    	else if(battername.equals("広島")||battername.equals("広島東洋カープ")){
    		member[1]="梵英心";
    		member[2]="東出輝裕";
    		member[3]="廣瀬純";
    		member[4]="ニック";
    		member[5]="丸佳浩";
    		member[6]="堂林翔太";
    		member[7]="松山竜平";
    		member[8]="白濱裕太";
    		member[9]="岩本貴裕";
    		
    		for(int i=1;i<=9;i++){
    			myTextArea.append(i + "番 : " + member[i]+ "\n");
    		}
    		number=9;
    	}
    	else if(battername.equals("巨人")||battername.equals("読売ジャイアンツ")){
    		member[1]="長野久義";
    		member[2]="松本哲也";
    		member[3]="坂本勇人";
    		member[4]="阿部慎之助";
    		member[5]="村田修一";
    		member[6]="高橋由伸";
    		member[7]="亀井義行";
    		member[8]="中井大介";
    		member[9]="高橋由伸";
    		
    		for(int i=1;i<=9;i++){
    			myTextArea.append(i + "番 : " + member[i] + "\n");
    		}
    		number=9;
    	}
    	else{
    		number +=1;
    		member[number] = battername;
			myTextArea.append(number + "番 : " + battername + "\n");
    	}
    }
	
	void calc(){
		if(number==9){
			number = 0;
			myTextArea.append("\n\n--- 計算中・・・ ---\n\n--- 少々お待ちください・・・ ---\n\n");
			for(int i=1;i<=9;i++){
				startingmember[i] = member[i];
			}
			Caliculate cal = new Caliculate(startingmember);
			String result1 = cal.result1;
			String result2 = cal.result2;
			myTextArea.append("\n\n--- 計算結果が出ました!! ---\n\n");
			myTextArea.append(result1+"\n\n"+result2);
			number =0;
		}
		else{
			myTextArea.append("\n\n --- Error --- \n\n野球は９人で行うスポーツです！！\n\nスターティングメンバーの入力\n\n");
			number = 0;
		}
	}
	
public void init(){
        myTextField1 = new MyTextField(this,battername);
        new MyGoButton(this); //this:インスタンス自身を参照
        new MyQuitButton(this);
    	new MyStartButton(this);
        myTextArea = new MyTextArea(this);
    }
	public static void main(String[] args){
		BattingSimulator3 br = new BattingSimulator3();
        br.init();
        Frame fr = new Frame();
        fr.add("Center", br);
        fr.setSize(600,600);
        fr.setVisible(true);
	}
}


class Data{ // データ読み込み。
	
	String searchbatter;
	double avg,obp,hit,hit2,hit3,hit4,atbat,bat,swing;
	
	Data(String searchbatter1){
		searchbatter = searchbatter1;
		search();
	}
	
	void search(){
		try{
			File file = new File("Data.txt");
			
			if (checkBeforeReadfile(file)){
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;	
				while((line = br.readLine()) != null){
					
					Pattern battername = Pattern.compile("(\\S+)(.*)");
					Matcher matcher0  = battername.matcher(line);
					if(matcher0.find()){
						String header = matcher0.group(1);
						if(header.equals(searchbatter)){
							
							//打者名  打率　打席数　ナ　二塁打　三塁打　本塁打　三振　　出塁率　　
							Pattern pattern = Pattern.compile("(\\S+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s+)(\\d+)(\\s*)");
							Matcher matcher1 = pattern.matcher(line);
							if(matcher1.find()){
								avg   = Integer.valueOf(matcher1.group(3));
								atbat = Integer.valueOf(matcher1.group(5));
								bat   = Integer.valueOf(matcher1.group(7));
								hit   = Integer.valueOf(matcher1.group(9));
								hit2  = Integer.valueOf(matcher1.group(11));
								hit3  = Integer.valueOf(matcher1.group(13));
								hit4  = Integer.valueOf(matcher1.group(15));
								swing = Integer.valueOf(matcher1.group(17));
								obp   = Integer.valueOf(matcher1.group(19));
							}
						}
					}
				}
				br.close();
			}
			else{
				System.out.println("ファイルが見つからないか開けません");
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e);
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
	
	private static boolean checkBeforeReadfile(File file){
	if (file.exists()){
		if (file.isFile() && file.canRead()){
			return true;
		}
	}
	return false;
	}
}
