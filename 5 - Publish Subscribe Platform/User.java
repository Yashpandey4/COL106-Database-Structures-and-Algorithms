import java.util.ArrayList;

public class User
{
    String uid;
    ArrayList<Subs> subList;
    Read read;

    public User()
    {
        uid="";
        subList=new ArrayList<>();
        read=new Read();
    }

    public User(String id)
    {
        uid=id;
        subList=new ArrayList<>();
        read=new Read();
    }
    
    public void addSub(String id, int t)
    {
        Subs sub=new Subs(t, id);
        if(containsSubWithID(id))
        {
            for(int i=0;i<subList.size();i++)
            {
                if(subList.get(i).pid.equalsIgnoreCase(id))
                {
                    if(subList.get(i).status)
                        return;
                    else
                    {
                        subList.get(i).status=true;
                        subList.get(i).time=t;
                        return;
                    }
                }
            }
        }
        subList.add(sub);
    }

    public boolean removeSub(String id, int t)
    {
        boolean flag=false;
        Subs sub=getSubWithID(id);
        if(sub.pid.length()<1)
            System.out.println("Error - You have not subcribed to user with ID "+id+" so you cannot unsubscribe either.");
        else if(t<sub.time)
            System.out.println("Error - You have cannot unsubcribed to user with ID "+id+" before you have subscribed to him.");
        else if(!sub.status)
            System.out.println("Error - UID "+this.uid+" has not subscribed to PID "+id);
        else if(sub.status)
        {
            subList.get(subList.indexOf(sub)).status=false;
            subList.get(subList.indexOf(sub)).time=t;
            flag=true;
        }
        return flag;
    }

    public boolean containsSubWithID(String id)
    {
        for(int i=0;i<subList.size();i++)
        {
            if(subList.get(i).pid.equalsIgnoreCase(id))
                return true;
        }
        return false;
    }

    public Subs getSubWithID(String id)
    {
        for(int i=0;i<subList.size();i++)
        {
            if(subList.get(i).pid.equalsIgnoreCase(id))
                return subList.get(i);
        }
        return (new Subs());
    }
}