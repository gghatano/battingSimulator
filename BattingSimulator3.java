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
import java.util.regex.Pattern; // ���K�\��

class GameNumber{ // number of games
  public int getGameNumber(){
    return 100;
  }
}

class Coordinate{ //�I�薼�Ɍl���т��܂Ƃ߂�
	double  avg,hit4,p_hit1,p_hit2,p_hit3,p_hit4,p_swing,p_other;
	double longhit;
	String battername;
	
	Coordinate(String batternamea,double avga,double hit1a,double hit2a,double hit3a,double hit4a,double atbata,double swinga,double othera){
		
		battername = batternamea;
		avg = avga;
		hit4 = hit4a;
		p_hit1 = hit1a / atbata; // �P�ŗ�
		p_hit2 = hit2a / atbata; // ��ۑŗ�
		p_hit3 = hit3a / atbata; // �O�ۑŗ�
		p_hit4 = hit4a / atbata; // �z�[��������
		p_swing= swinga/ atbata; // �O�U��
		p_other= othera/ atbata; // ���̑��o�ۗ�
		longhit = (hit1a*1+hit2a*2+hit3a*3+hit4a*4) / atbata; // ���ŗ�
	}
}

class Offence{ // �ŎҖ��ƃx�[�X��Ԃ���͂���ƁA�ŐȌ��ʂ��v�Z����
	Coordinate batter;
	int first, second, third; // �x�[�X���
	int score;              // ���_
	int outcount;           // �A�E�g�J�E���g
	int hitting;			// �P�����̗ݐ��~q�b�g��
	int fourball;			// �P�����̎l������
	
	Offence(Coordinate batter1,int b11,int b21,int b31,int outcount1,int score1, int hittings,int fourball1){ // �f�[�^�̑��
		batter = batter1;
		first = b11 ; second = b21 ; third = b31;
		outcount = outcount1;
		score = score1;
		hitting = hittings;
		fourball = fourball1;
		attack();
	}
	
	void attack(){ // �Ō����ʂ̕���
		double random = Math.random();
		if(random<=batter.p_hit1){ // �P��
			base(1);
		}
		else if(random<=batter.p_hit1+batter.p_hit2){ // ��ۑ�
			base(2);
		}
		else if(random<=batter.p_hit1+batter.p_hit2+batter.p_hit3){// �O�ۑ�
			base(3);
		}
		else if(random<=batter.p_hit1+batter.p_hit2+batter.p_hit3+batter.p_hit4){// �z�[������
			base(4);
		}
		else if(random<=batter.p_hit1+batter.p_hit2+batter.p_hit3+batter.p_hit4+batter.p_swing){// �O�U
			base(0);
		}
		
		else if(random<=batter.p_hit1+batter.p_hit2+batter.p_hit3+batter.p_hit4+batter.p_swing+batter.p_other){// ���̑��o��
			base(5);
		}
		
		else{ // �q�b�e�B���O�A�E�g
			base(6);
		}
	}
	
	void base(int result){ // �x�[�X��Ԃ̕ω�
		int getscore=0;
		double timely;
			if(batter.longhit>0.5)	timely = 0.8;
			else					timely = 0.5;
		double timelyhit = Math.random(); // �^�C�����[�q�b�g�ɂȂ�m��(�K��)
	
		switch(result){
			case 0:// �O�U
				//System.out.println("swingout!!");
				outcount +=1;
			break;
			
			case 1: // �P��
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
			
			case 2:// ��ۑ�
				//System.out.println("double!!");
				hitting += 1;
				score += second+third;
				third = first; second=1; first=0;
			break;
			
			case 3:// �O�ۑ�
				//System.out.println("triple!!");
				hitting += 1;
				score += third+second+first;
				third=1;second=0;first=0;
			break;
			
			case 4:// �{�ۑ�
				//System.out.println("homerun!!");
				hitting += 1;
				score += first + second + third + 1;
				first = 0; second = 0; first = 0;
			break;
			
			case 5:// ���̑��o��
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
			
			case 6:// �q�b�e�B���O�A�E�g
				//System.out.println("out!!");
				if(first==1&&second==0){ // �_�u���v���[����
                             // ����͓K��
					if(Math.random()<0.700){
						outcount+=2;
						first = 0 ; second = 0;
					}
					else{
						outcount += 1;
						first = 0 ; second = 1;
					}
				}
				
				else{ // �i�ۑ�
          // ����͓K��
					if(Math.random()<0.600) outcount+=1; //�i�ۑŎ��s
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


class Batting{ // �Ō��\�͂̌v�Z
    GameNumber gamenumber = new GameNumber();
		int game_number = gamenumber.getGameNumber(); // ���s��
		String battername ; 
		String result;
		MyTextArea area;
	
		Coordinate batter[] = new Coordinate[10];
	
		int outcount = 0,score=0,b1=0,b2=0,b3=0;
		int inning = 1;
		int hitting = 0;
		int scoreall = 0;
	
		double scoreavg = 0; // ���ϓ��_
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
			
			
			result = "   --- �v�Z���� ---\n\n";
			
			result += "�E���s�� : "+Integer.toString(game_number)+"����\n\n1�������ϓ��_ : "+Double.toString(scoreavg)+"\n";
			result += "�E1���������q�b�g�� : "+Double.toString( hittingavg) +"\n";
			result += "�E1�������ϑŐȐ� : "+ Double.toString( dasekisuuavg) + "\n";
			result += "�E1�������ώl������ :"+Double.toString(fourballavg)+"\n";
			result += "�E�`�[���ŗ� :"+Integer.toString(calclateavg) + "\n\n";
		}
}


class Caliculate{
	Coordinate[] batter = new Coordinate[10]; // �����Ő�
	Coordinate[] batter_shuffle = new Coordinate[10];//����ւ��Ő�
	Coordinate[] batter_opt = new Coordinate[10]; // �œK�Ő�
	
	Coordinate batter_temp;
	
	Data data[] = new Data[10];
	String member[] = new String[9];
	double score1,score2,scoremax;
	Batting team1,team2,teamtemp;
	String result1="",result2="",resulttemp;
	int change = 0;
	
	
	int changelimit = 1000; // �ݐϑŏ��ύX�񐔂Ƃ��̏��
	
	
	int nullpo = 0; // �����I���J�E���g
	
	Caliculate(String[] member1){
		member = member1;
		run();
		shuffle();
	}
	// �Ŏ҃f�[�^�v�f�F���O�A�ŗ��A�o�ۗ��A�i���A��ۑŐ��A�O�ۑŐ��A�{�ۑŐ��A�ŐȐ��A�Ő��A�O�U��
	double avg,obp,hit,hit1,hit2,hit3,hit4,atbat,bat,swing,other;
	
	void run(){
		for(int i=1;i<=9;i++){
			data[i]  = new Data(member[i]);// �Ŏ҂̖��O����͂���ƁA�������n�����B
			avg = data[i].avg/1000.0 ; obp = data[i].obp/1000.0;
			hit = data[i].hit;  hit2 = data[i].hit2 ; hit3 = data[i].hit3 ; hit4 = data[i].hit4;
			atbat = data[i].atbat ; bat = data[i].bat ; swing = data[i].swing ;
			other = atbat - bat;
			hit1  = hit - hit2 - hit3 - hit4; 
			//�@���O�@�ŗ��@�P�Ő��@��ۑŐ��@�O�ۑŐ��@�{�ۑŐ��@�ŐȐ��@�O�U���@���̑��o��
			batter[i] = new Coordinate(member[i],avg,hit1,hit2,hit3,hit4,atbat,swing,other); // �����܂Ƃ߂�
		}
		batter_shuffle = batter;
		
		team1 = new Batting(batter);
		team1.run();
		score1 = team1.scoreavg;
		result1 += "\n\n   --- ���͂����Ő� ---\n";
		for(int j=1;j<=9;j++){
			//System.out.println(j + "�� " + batter[j].battername);
			//System.out.println("�ŗ��F."+(int)(batter[j].avg*1000)+" �{�ۑŁF"+(int)batter[j].hit4+"\n");
			
			result1 += "\n"+Integer.toString(j) + "�� : "+ batter[j].battername;
			result1 += "  �ŗ�:" +Integer.toString( (int)(batter[j].avg*1000))+" �{�ۑŁF"+Integer.toString( (int)batter[j].hit4) +"\n";
		}
	}
	
	void shuffle(){
		while(change < changelimit){
			int random1 = (int)(Math.random()*9+1),random2 = (int)(Math.random()*9+1);
			
			if(random1-random2> 0.001){// �����_������������Ȃ��E�X��A�ŏ�����ւ����s
				
				//System.out.println("����ւ� "+ random1+" �� "+random2);
				
				change += 1; // �ύX�񐔂�+1����B
				batter_temp = batter_shuffle[random1];
				batter_shuffle[random1] = batter_shuffle[random2];
				batter_shuffle[random2]= batter_temp;           //����ւ�
			
				team2 = new Batting(batter_shuffle);
				team2.run();
				score2 = team2.scoreavg; // ���ϓ��_�̎Z�o
				
				if(score2>score1){ // ����ւ������ʁA���_��������I�E�X�ꍇ
					batter_opt = batter_shuffle; // ���ʂ�opt�Ɏ�E�g����
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
			//System.out.println("�œK�������ŏ�\n\n");
			result2 += "   --- �œK�������ŏ� ---\n\n";
			for(int j=1;j<=9;j++){
				//System.out.println(j + "�� : " + batter_opt[j].battername );
				//System.out.println("�ŗ��F"+(int)(batter_opt[j].avg*1000)+" �{�ۑŁF"+(int)batter_opt[j].hit4+"\n\n");
				result2 += Integer.toString(j) + "�ԁ@"+ batter_opt[j].battername;
				result2 += "  �ŗ��F" + Integer.toString((int)(batter_opt[j].avg*1000) ) +" �{�ۑŁF"+Integer.toString( (int)batter_opt[j].hit4 ) +"\n\n";
			}
			result2 += "\n\n" + resulttemp;
			//System.out.println(result2);
		}
		catch(NullPointerException e){
			result1 = "";
			result2 = "";
			nullpo += 1;
			if(nullpo>10){
				System.out.println("�G���[\n");
				System.exit(0); // �����I��
			}
			run();
			shuffle();
		}
	}
}

class MyTextField extends TextField{
    MyTextField(BattingSimulator3 br,String str){
        super("���l",25);
        br.add(this);
    }
}

class MyTextArea extends TextArea{
    MyTextArea(BattingSimulator3 br){
        super("\n�I�薼���`�[��������͂���GO�{�^���������Ă��������B\n\n1�Ԃ���9�Ԃ܂œ��͂�����ASTART�{�^���������ƌv�Z���J�n����܂��B\n\n�X�^�[�e�B���O�����o�[�̓���\n\n",30,60,TextArea.SCROLLBARS_BOTH);//������
        setEditable(false); //�ҏW�s��
        setFont(new Font("Helvetica",Font.PLAIN,12));//�t�H���g�̐ݒ�
        br.add(this);//�u���E�U��ɔz�u
    }
}

abstract class MyButton extends Button implements ActionListener{

	final BattingSimulator3 browser;    

	MyButton(BattingSimulator3 br,String name){
		super(name);
		browser = br;
		browser.add(this); //�u���E�U��ɔz�u
		addActionListener(this);
    }
    abstract public void actionPerformed(ActionEvent e);
    //�u��������v�̋�̓I���e���`����
}

class MyGoButton extends MyButton{
    MyGoButton(BattingSimulator3 br){
        super(br,"Go!");
    }
    public void actionPerformed(ActionEvent e){
        browser.go();
        //Go�{�^���������ꂽ��ABattingSimulator3��go()
    }
}

class MyQuitButton extends MyButton{
    MyQuitButton(BattingSimulator3 br){
        super(br,"Quit!");      
    }
    public void actionPerformed(ActionEvent e){
        browser.quit();
        //Quit�{�^���������ꂽ��ABattingSimulator3�S�̂�quit
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
    //��o���́AApplet�Ƃ��Ē�o�B
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
    	
    	if(battername.equals("��_")||battername.equals("��_�^�C�K�[�X")){
    		member[1]="�}�[�g��";
    		member[2]="����b��";
    		member[3]="���J�h";
    		member[4]="�V��M�_";
    		member[5]="�u���[��";
    		member[6]="���{�m��";
    		member[7]="�r��";
    		member[8]="���Џ�";
    		member[9]="�铇���i";
    		
    		for(int i=1;i<=9;i++){
    			myTextArea.append(i + "�� : " + member[i] + "\n");
    		};
    		number=9;
    	}
    	else if(battername.equals("�L��")||battername.equals("�L�����m�J�[�v")){
    		member[1]="���p�S";
    		member[2]="���o�P�T";
    		member[3]="�A����";
    		member[4]="�j�b�N";
    		member[5]="�ۉ��_";
    		member[6]="�����đ�";
    		member[7]="���R����";
    		member[8]="���_�T��";
    		member[9]="��{�M�T";
    		
    		for(int i=1;i<=9;i++){
    			myTextArea.append(i + "�� : " + member[i]+ "\n");
    		}
    		number=9;
    	}
    	else if(battername.equals("���l")||battername.equals("�ǔ��W���C�A���c")){
    		member[1]="����v�`";
    		member[2]="���{�N��";
    		member[3]="��{�E�l";
    		member[4]="�����T�V��";
    		member[5]="���c�C��";
    		member[6]="�����R�L";
    		member[7]="�T��`�s";
    		member[8]="������";
    		member[9]="�����R�L";
    		
    		for(int i=1;i<=9;i++){
    			myTextArea.append(i + "�� : " + member[i] + "\n");
    		}
    		number=9;
    	}
    	else{
    		number +=1;
    		member[number] = battername;
			myTextArea.append(number + "�� : " + battername + "\n");
    	}
    }
	
	void calc(){
		if(number==9){
			number = 0;
			myTextArea.append("\n\n--- �v�Z���E�E�E ---\n\n--- ���X���҂����������E�E�E ---\n\n");
			for(int i=1;i<=9;i++){
				startingmember[i] = member[i];
			}
			Caliculate cal = new Caliculate(startingmember);
			String result1 = cal.result1;
			String result2 = cal.result2;
			myTextArea.append("\n\n--- �v�Z���ʂ��o�܂���!! ---\n\n");
			myTextArea.append(result1+"\n\n"+result2);
			number =0;
		}
		else{
			myTextArea.append("\n\n --- Error --- \n\n�싅�͂X�l�ōs���X�|�[�c�ł��I�I\n\n�X�^�[�e�B���O�����o�[�̓���\n\n");
			number = 0;
		}
	}
	
public void init(){
        myTextField1 = new MyTextField(this,battername);
        new MyGoButton(this); //this:�C���X�^���X���g���Q��
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


class Data{ // �f�[�^�ǂݍ��݁B
	
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
							
							//�ŎҖ�  �ŗ��@�ŐȐ��@�i�@��ۑŁ@�O�ۑŁ@�{�ۑŁ@�O�U�@�@�o�ۗ��@�@
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
				System.out.println("�t�@�C����������Ȃ����J���܂���");
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
