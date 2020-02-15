public class Subs
{
    String pid;
    int time;
    boolean status;

    public Subs()
    {
        pid="";
        time=-1;
        status=false;
    } 

    public Subs(int time, String pid)
    {
        this.time=time;
        this.pid=pid;
        status=true;
    }
}