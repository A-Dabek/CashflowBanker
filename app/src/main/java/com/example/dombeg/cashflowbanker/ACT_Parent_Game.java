package com.example.dombeg.cashflowbanker;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class ACT_Parent_Game extends ACT_Parent {
    //==============================================================================================
    //==============================================================================================
    enum en_code{S, P, B, D, N}
    //==============================================================================================
    //==============================================================================================
    protected void init_variables()
    {
        super.init_variables();
        act_game_player = new Player();
    }
    //==============================================================================================
    //==============================================================================================
    protected class Buyable
    {
        private en_code code;
        private String name;
        private int quantity;
        private int price;
        private int income;

        public void set_code(en_code arg){code=arg;}
        public void set_name(String arg){name=arg;}
        public void set_quantity(int arg){quantity=arg;}
        public void set_price(int arg){price=arg;}
        public void set_income(int arg){income=arg;}

        public en_code get_code(){return code;}
        public String get_name(){return name;}
        public int get_quantity(){return quantity;}
        public int get_price(){return price;}
        public int get_income(){return income;}

        public Buyable() {
            code = en_code.N;
            name = "???";
            quantity = 0;
            price = 0;
            income = 0;
        }
        public Buyable(en_code arg0, String arg1, int quan_arg2, int pri_arg3, int inc_arg4) {
            code = arg0;
            name = arg1;
            quantity = quan_arg2;
            price = pri_arg3;
            income = inc_arg4;
        }
        public Buyable(Buyable cp)
        {
            code = cp.get_code();
            name = cp.get_name();
            quantity = cp.get_quantity();
            price = cp.get_price();
            income = cp.get_income();
        }
        public String Display()
        {
            String text = "";
            return code.name() + ":\t" + name + "\t" + ((income < 0) ? ("-$"+String.valueOf(income)) : ("+$"+String.valueOf(income))) + " x " + String.valueOf(quantity);
        }
    }
    //==============================================================================================
    //==============================================================================================
    protected class Loan
    {
        private int amount;
        private int installment;
        public void set_amount(int arg){amount=arg;}
        public void set_instalment(int arg){installment=arg;}

        public int get_amount(){return amount;}
        public int get_instalment(){return installment;}

        public Loan()
        {
            set_amount(0);
            set_instalment(0);
        }
        public Loan(int ins, int amn)
        {
            set_amount(amn);
            set_instalment(ins);
        }
        public Loan copy()
        {
            return new Loan(this.get_instalment(), this.get_amount());
        }
        public int payoff()
        {
            int amn_temp = get_amount();
            set_amount(0);
            set_instalment(0);
            return -amn_temp;
        }
        public void takeout(int amn, int ins)
        {
            set_amount(amn);
            set_instalment(ins);
        }
        public int income() {return -get_instalment();}
        public String display(){return "$" + String.valueOf(get_instalment()) + "/" + "$" + String.valueOf(get_amount());}
    }
    //==============================================================================================
    //==============================================================================================
    protected class Work
    {
        private int ID;
        private String name;
        private int salary;
        private int taxes;
        private Loan l_mor,l_edu, l_car, l_card, l_kons;
        private int other;
        private int per_kid;
        private int savings;

        public void set_id(int arg){ID=arg;}
        public void set_name(String arg){name=arg;}
        public void set_salary(int arg){salary=arg;}
        public void set_taxes(int arg){taxes=arg;}
        public void set_loan_mor(Loan arg){l_mor=arg;}
        public void set_loan_edu(Loan arg){l_edu=arg;}
        public void set_loan_car(Loan arg){l_car=arg;}
        public void set_loan_card(Loan arg){l_card=arg;}
        public void set_loan_kons(Loan arg){l_kons=arg;}
        public void set_other(int arg){other=arg;}
        public void set_per_kid(int arg){per_kid=arg;}
        public void set_savings(int arg){savings=arg;}

        public int get_id(){return ID;}
        public String get_name(){return name;}
        public int get_salary(){return salary;}
        public int get_taxes(){return taxes;}
        public Loan get_loan_mor(){return l_mor;}
        public Loan get_loan_edu(){return l_edu;}
        public Loan get_loan_car(){return l_car;}
        public Loan get_loan_card(){return l_card;}
        public Loan get_loan_kons(){return l_kons;}
        public int get_other(){return other;}
        public int get_per_kid(){return per_kid;}
        public int get_savings(){return savings;}

        public int get_self_loanvector()
        {
            int vector =  0;
            if(l_car.income() != 0) vector |= 1;
            if(l_mor.income() != 0) vector |= 1<<1;
            if(l_edu.income() != 0) vector |= 1<<2;
            if(l_card.income() != 0) vector |= 1<<3;
            if(l_kons.income() != 0) vector |= 1<<4;
            return vector;
        }
        public Work()
        {
            ID = -1;
            name = "??";
            salary=0;
            taxes=0;
            other=0;
            per_kid=0;
            savings=0;
            l_mor = new Loan();
            l_edu = new Loan();
            l_car = new Loan();
            l_card = new Loan();
            l_kons = new Loan();

        }
        public Work(int id, String nm, int slr, int tx, int sav, int oth, int pkid, Loan mor, Loan edu, Loan car, Loan card, Loan kons)
        {
            ID = id;
            name = nm;
            salary=slr;
            taxes=tx;
            other=oth;
            per_kid=pkid;
            savings=sav;
            l_mor = mor.copy();
            l_edu = edu.copy();
            l_car = car.copy();
            l_card = card.copy();
            l_kons = kons.copy();
        }
        public Work copy()
        {
            return new Work(this.get_id(),this.name, this.salary, this.taxes, this.savings, this.other, this.per_kid, this.l_mor, this.l_edu, this.l_car, this.l_card, this.l_kons);
        }
        public int savings_income()
        {
            return savings;
        }
        public int salary()
        {
            return salary;
        }
        public int loan_outcome(int vector)
        {
            int outcome =  0;
            if((vector & (1)) != 0) outcome += l_car.income();
            if((vector & (1<<1)) != 0) outcome += l_mor.income();
            if((vector & (1<<2)) != 0) outcome += l_edu.income();
            if((vector & (1<<3)) != 0) outcome += l_card.income();
            if((vector & (1<<4)) != 0) outcome += l_kons.income();
            return outcome;
        }
        public int overall_outcome(int vector)
        {
            return -taxes-other-loan_outcome(vector);
        }
        public int kid_cost()
        {
            return -per_kid;
        }
    }
    final Work[] cashflow_works =
            {
                    new Work(0,"PILOT", 9500, 2350, 400, 2210, 480, new Loan(1330,143000), new Loan(0,0), new Loan(300,15000), new Loan(660,22000), new Loan(50,1000)),
                    new Work(1,"INŻYNIER", 4900, 2350, 400, 1090, 250, new Loan(700,75000), new Loan(60,12000), new Loan(140,7000), new Loan(120,4000), new Loan(50,1000)),
                    new Work(2,"POLICJANT", 3000, 580, 520, 690, 160, new Loan(400,46000), new Loan(0,0), new Loan(100,5000), new Loan(60,2000), new Loan(50,1000)),
                    new Work(3,"DOZORCA", 1600, 280, 560, 300, 70, new Loan(200,20000), new Loan(0,0), new Loan(60,4000), new Loan(60,2000), new Loan(50,1000)),
                    new Work(4,"PRAWNIK", 7500, 1830, 400, 1650, 380, new Loan(1100,115000), new Loan(390,78000), new Loan(220,11000), new Loan(180,6000), new Loan(50,1000)),
                    new Work(5,"MENEDŻER", 4600, 910, 400, 1000, 240, new Loan(700,75000), new Loan(60,12000), new Loan(120,6000), new Loan(90,3000), new Loan(50,1000)),
                    new Work(6,"NAUCZYCIEL", 3300, 630, 400, 760, 180, new Loan(500,50000), new Loan(60,12000), new Loan(100,5000), new Loan(90,3000), new Loan(50,1000)),
                    new Work(7,"KIEROWCA", 2500, 460, 750, 570, 140, new Loan(400,38000), new Loan(0,0), new Loan(80,4000), new Loan(60,2000), new Loan(50,1000)),
                    new Work(8,"PIELĘGNIARKA", 2500, 600, 480, 710, 170, new Loan(400,47000), new Loan(30,6000), new Loan(100,5000), new Loan(90,3000), new Loan(50,1000)),
                    new Work(9,"SEKRETARKA", 2500, 460, 710, 570, 140, new Loan(400,38000), new Loan(0,0), new Loan(80,4000), new Loan(60,2000), new Loan(50,1000)),
                    new Work(10,"MECHANIK", 2000, 360, 670, 450, 110, new Loan(300,31000), new Loan(0,0), new Loan(60,3000), new Loan(60,2000), new Loan(50,1000)),
                    new Work(11,"LEKARZ", 13200, 3420, 400, 2880, 640, new Loan(1900,202000), new Loan(750,150000), new Loan(380,19000), new Loan(270,9000), new Loan(50,1000))
            };
    //==============================================================================================
    //==============================================================================================

    //==============================================================================================
    //==============================================================================================
    protected class Player
    {
        private int color;
        private int wealth;
        private String name;
        private int kids;
        private int loan_vector;
        private boolean savings_avail;
        private int job;
        private Loan l_bank;
        private final Buyable[] finance;

        public void set_color(int arg){color=arg;}
        public void set_wealth(int arg){wealth=arg;}
        public void set_name(String arg){name=arg;}

        public int get_color(){return color;}
        public int get_wealth(){return wealth;}
        public String get_name(){return name;}

        public Player()
        {
            wealth = 0;
            color = 0;
            kids = 0;
            job = 0;
            l_bank = new Loan();
            finance = new Buyable[15];

            for(int i=0; i<finance.length; i++)
            {
                finance[i] = new Buyable();
            }
        }
        public void set_kids(int arg){kids=arg;}
        public void set_loan_vector(int arg){loan_vector=arg;}
        public void set_savings_availability(boolean arg){savings_avail=arg;}
        public void set_job(int arg){job=arg;}
        public void set_loan_bank(Loan arg){l_bank=arg;}
        public void set_buyable(int index, Buyable arg){finance[index]=arg;}

        public int get_kids(){return kids;}
        public int get_loan_vector(){return loan_vector;}
        public boolean get_savings_availability(){return savings_avail;}
        public int get_job(){return job;}
        public Loan get_loan_bank(){return l_bank;}
        public Buyable get_buyable(int index) {
            if (index < finance.length) return finance[index];
            else return null;
        }
        public int get_buyable_size(){return finance.length;}
        public int get_bankloan_instal(){return l_bank.get_instalment();}

        public void take_up_loan(int amount)
        {
            wealth += amount;
            l_bank = new Loan(amount/10, amount);
        }
        public void pay_off_loan(int index)
        {
            wealth -= get_loan_amount(index);
            if(index == 6){
                l_bank = new Loan(0,0);
            }
            else {
                int new_vector = 0;
                for(int i=0; i<6;i++ )
                {
                    if(i==index) continue;
                    if( (loan_vector & 1<<i) > 0) new_vector |= 1<<i;
                }
                loan_vector = new_vector;
            }
        }
        public int get_loan_amount(int index)
        {
            Work temp = cashflow_works[job];
            switch(index)
            {
                case 0:
                    return temp.get_loan_car().get_amount();
                case 1:
                    return temp.get_loan_mor().get_amount();
                case 2:
                    return temp.get_loan_edu().get_amount();
                case 3:
                    return temp.get_loan_card().get_amount();
                case 4:
                    return temp.get_loan_kons().get_amount();
                case 5:
                    return get_loan_bank().get_amount();
                default:
                    return -1;
            }
        }
        public int get_loan_instalment(int index)
        {
            Work temp = cashflow_works[job];
            switch(index)
            {
                case 0:
                    return temp.get_loan_car().get_instalment();
                case 1:
                    return temp.get_loan_mor().get_instalment();
                case 2:
                    return temp.get_loan_edu().get_instalment();
                case 3:
                    return temp.get_loan_card().get_instalment();
                case 4:
                    return temp.get_loan_kons().get_instalment();
                case 5:
                    return get_loan_bank().get_instalment();
                default:
                    return -1;
            }
        }
        public void change_wealth(int amount)
        {
            wealth += amount;
        }
        public int buyable_income()
        {
            int sum = 0;
            for(Buyable i : finance) sum += i.get_income();
            return sum;
        }
        public int overall_income()
        {
            return (savings_avail ? cashflow_works[job].salary()+cashflow_works[job].savings_income() : cashflow_works[job].salary()) + buyable_income();
        }
        public int overall_outcome()
        {
            return cashflow_works[job].overall_outcome(loan_vector)+l_bank.income()+kids*cashflow_works[job].kid_cost();
        }
        public int income()
        {
            int result = overall_income()+overall_outcome();
            savings_avail = false;
            return result;
        }
        public int getWork()
        {
            return job;
        }
        public void add_kid()
        {
            kids += 1;
            if(kids > 3) kids = 3;
        }

        public int sell_buyable(int index) {
            if(finance[index] == null) return 0;
            int money = finance[index].price;
            finance[index].set_code(en_code.N);
            return money;
        }
        public int get_free_buyable_index()
        {
            for(int i=0; i<finance.length; i++){
                if(finance[i].get_code() == en_code.N) return i;
            }
            return -1;
        }
    }
    Player act_game_player;
    //==============================================================================================
    //==============================================================================================
    private final static int lines_per_player = 9;
    Player act_gamefile_read_player()//
    {
        Player temp_player = new Player();
        ArrayList<String>data;

        data = read_first_player("game00.txt");
        Buyable by_temp = new Buyable();
        for(int i=0; i<data.size() ;i++)
        {
            String player_line = data.get(i);
            if(player_line.contains("OVER")) break;
            switch(i)
            {
                case 0:
                    temp_player.set_name(player_line);
                    break;
                case 1:
                    temp_player.set_color(Integer.valueOf(player_line));
                    break;
                case 2:
                    temp_player.set_job(Integer.parseInt(player_line));
                    break;
                case 3:
                    temp_player.set_wealth(Integer.parseInt(player_line));
                    break;
                case 4:
                    temp_player.set_loan_vector(Integer.parseInt(player_line));
                    break;
                case 5:
                    temp_player.set_loan_bank(new Loan(Integer.parseInt(player_line), Integer.parseInt(player_line) / 10));
                    break;
                case 6:
                    temp_player.set_savings_availability(Boolean.parseBoolean(player_line));
                    break;
                case 7:
                    temp_player.set_kids(Integer.parseInt(player_line));
                    break;
                default:
                    for(int j=0; j<5; i++, j++)
                    {
                        player_line = data.get(i);
                        if(player_line.contains("OVER")) break;
                        switch(j)
                        {
                            case 0:
                                switch (player_line)
                                {
                                    case "S":
                                        by_temp.set_code(en_code.S);
                                        break;
                                    case "D":
                                        by_temp.set_code(en_code.D);
                                        break;
                                    case "P":
                                        by_temp.set_code(en_code.P);
                                        break;
                                    case "B":
                                        by_temp.set_code(en_code.B);
                                        break;
                                }
                                break;
                            case 1:
                                by_temp.set_name(player_line);
                                break;
                            case 2:
                                by_temp.set_quantity(Integer.parseInt(player_line));
                                break;
                            case 3:
                                by_temp.set_price(Integer.parseInt(player_line));
                                break;
                            case 4:
                                by_temp.set_income(Integer.parseInt(player_line));
                                break;
                        }
                    }
            }

        }
        return temp_player;
    }
    void act_gamefile_overwrite(Player arg_player)
    {
        String data = player_data();
        data = data + "\n" + read_other_players("game00.txt");
        writeToFile("game00.txt", data);
    }
    void act_gamefile_update(Player arg_player)
    {
        String data = read_other_players("game00.txt");
        data = data.substring(0,data.length()-4);
        data = data + player_data() + "\n";
        data += "END";
        writeToFile("game00.txt", data);
    }
    private String player_data()
    {
        String player_data = "";
        player_data += act_game_player.get_name() + "\n";
        player_data += String.valueOf(act_game_player.get_color()) + "\n";
        player_data += String.valueOf(act_game_player.get_job()) + "\n";
        player_data += String.valueOf(act_game_player.get_wealth()) + "\n";
        player_data += String.valueOf(act_game_player.get_loan_vector()) + "\n";
        player_data += String.valueOf(act_game_player.get_loan_bank().get_amount()) + "\n";
        player_data += String.valueOf(act_game_player.get_savings_availability()) + "\n";
        player_data += String.valueOf(act_game_player.get_kids()) + "\n";
        for(int i=0; i<act_game_player.get_buyable_size(); i++)
        {
            Buyable by_temp = act_game_player.get_buyable(i);
            if(by_temp.get_code() == en_code.N) continue;
            player_data += by_temp.get_code() + "\n";
            player_data += by_temp.get_name() + "\n";
            player_data += by_temp.get_quantity() + "\n";
            player_data += by_temp.get_price() + "\n";
            player_data += by_temp.get_income() + "\n";
        }
        player_data += "OVER";
        return player_data;
    }
    //==============================================================================================
    //==============================================================================================
    private ArrayList<String> read_first_player(String path)
    {
        ArrayList<String> ret = new ArrayList();
        String line = "";
        try {
            InputStream inputStream = openFileInput(path);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while(!line.contains("OVER"))
                {
                    line = bufferedReader.readLine();
                    if(line == null) break;
                    else ret.add(line);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }
    //==============================================================================================
    //==============================================================================================
    private String read_other_players(String path)
    {
        String line;
        StringBuilder collect = new StringBuilder();
        InputStream inputStream;
        try {
            inputStream = openFileInput(path);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                boolean start_reading = false;
                while(!start_reading)
                {
                    line = bufferedReader.readLine();
                    if(line == null) break;
                    else if(line.contains("OVER")) start_reading = true;
                    while(start_reading)
                    {
                        line = bufferedReader.readLine() + "\n";
                        collect.append(line);
                        if(line.contains("END")) break;
                    }
                }
                bufferedReader.close();
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return collect.toString();
    }
    //==============================================================================================
    //==============================================================================================
    protected void act_gamefile_save(int saveSlot)//SAVES GAMEFILE INTO SAVESLOT
    {
        File path, savefile, gamefile;
        path = getApplicationContext().getFilesDir();
        savefile = new File(path, "save0" + String.valueOf(saveSlot) + ".txt");
        gamefile = new File(path, "game00.txt");
        if(savefile.exists()) savefile.delete();
        writeToFile(savefile.getPath(), readFromFile(gamefile.getPath(),0,true));
    }
    //==============================================================================================
    //==============================================================================================
    void act_initialize_gamefile()//READ NAMES AND CREATES NEW GAMEFILE
    {
        File path, gamefile;
        path = getApplicationContext().getFilesDir();
        gamefile = new File(path,"game00.txt");

        String[] gm_line = readFromFile(gamefile.getName(),1,lines_per_player,false);
        int clr_vector = Integer.parseInt(gm_line[0]);
        String gm_data = "";
        Random rand = new Random();
        int jobID = Math.abs(rand.nextInt(12));
        for(int i=1; i<gm_line.length; i++)
        {
            if(gm_line[i].contains("END")) break;
            if((clr_vector & 1<<(i-1)) == 0) continue;
            gm_data += gm_line[i]+"\n";
            gm_data += String.valueOf(i-1)+"\n";
            gm_data += String.valueOf(jobID)+"\n";
            gm_data += "0\n";
            jobID = Math.abs(rand.nextInt(12));
            gm_data += String.valueOf(cashflow_works[jobID].get_self_loanvector())+"\n";
            gm_data += "0\n";
            gm_data += "true\n";
            gm_data += "0\n";
            gm_data += "OVER\n";
        }
        gm_data += "END";
        writeToFile(gamefile.getName(), gm_data);
    }
    //==============================================================================================
    //==============================================================================================
    int getFreeSaveSlot()
    {
        int emptySlot = -1;
        File path, savefile;
        path = getApplicationContext().getFilesDir();
        for(int i=0; i<4;i++)
        {
            savefile = new File(path, "save0" + String.valueOf(i) + ".txt");
            if(readFromFile(savefile.getName(),1,false).equals("pusty zapis"))
            {
                emptySlot = i;
                break;
            }
        }
        if(emptySlot < 0) emptySlot = 0;
        return emptySlot;
    }
    //==============================================================================================
    //==============================================================================================
    void act_update_gamefile() {
        File path, gamefile;
        path = getApplicationContext().getFilesDir();
        gamefile = new File(path, "game00.txt");

        String player_data = "";
        player_data += act_game_player.get_name() + "\n";
        player_data += String.valueOf(act_game_player.get_color()) + "\n";
        player_data += String.valueOf(act_game_player.get_job()) + "\n";
        player_data += String.valueOf(act_game_player.get_wealth()) + "\n";
        player_data += String.valueOf(act_game_player.get_loan_vector()) + "\n";
        player_data += String.valueOf(act_game_player.get_loan_bank().get_amount()) + "\n";
        player_data += String.valueOf(act_game_player.get_savings_availability()) + "\n";
        player_data += String.valueOf(act_game_player.get_kids()) + "\n";
        player_data += "OVER";

        /*==============================
        Dombeg      //NAME
        2           //colorID
        5           //jobID
        12500       //money
        110101      //loan vector
        12500       //bank loan left
        true         //savings left
        0           //kids
        OVER
        END
        ==============================*/
        String other_data = "";
        String player_line;
        String re_writer = "";
        int last_line = 0;
        for(int i=1;!(player_line=readFromFile(gamefile.getName(),i,false)).contains("OVER") ;i++)
        {
            re_writer += player_line + "\n";
            last_line = i + 1;
            switch(i)
            {
                case 1:
                    act_game_player.set_name(player_line);
                    break;
                case 2:
                    act_game_player.set_color(Integer.parseInt(player_line));
                    break;
                case 3:
                    act_game_player.set_job(Integer.parseInt(player_line));
                    break;
                case 4:
                    act_game_player.set_wealth(Integer.parseInt(player_line));
                    break;
                case 5:
                    act_game_player.set_loan_vector(Integer.parseInt(player_line));
                    break;
                case 6:
                    act_game_player.set_loan_bank(new Loan(Integer.parseInt(player_line), Integer.parseInt(player_line) / 10));
                    break;
                case 7:
                    act_game_player.set_savings_availability(Boolean.parseBoolean(player_line));
                    break;
                case 8:
                    act_game_player.set_kids(Integer.parseInt(player_line));
                    break;
            }
        }
        re_writer += player_line + "\n";
        for(int i=1; ;i++)
        {
            player_line = readFromFile(gamefile.getName(),last_line+i,false);
            other_data += player_line + "\n";
            if(player_line.equals("OVER")) break;
        }
        writeToFile(gamefile.getName(), other_data + player_data + re_writer + "END");
    }
    //==============================================================================================
    //==============================================================================================
}
