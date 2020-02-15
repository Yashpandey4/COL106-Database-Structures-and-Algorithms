import java.util.*;

public class Read
{
    ArrayList<Integer> timeStamps;
    ArrayList<Post> outPost;
    ArrayList<Reply> outReply;

    class Tuple
    {
        String text;
        int time;
        Tuple()
        {
            text="";
            time=0;
        }
    }

    class Sortbytime implements Comparator<Tuple> 
    { 
        public int compare(Tuple a, Tuple b) 
        { 
            return a.time - b.time; 
        } 
    } 

    public Read()
    {
        timeStamps=new ArrayList<>();
        outPost=new ArrayList<>();
        outReply=new ArrayList<>();
        timeStamps.add(new Integer(-1));
    }

    public ArrayList<Tuple> performReadAtTime(int t, Platform p, User user)
    {
        int lastRead=timeStamps.get(timeStamps.size()-1);
        timeStamps.add(new Integer(t));
        if(t<=lastRead)
        {
            System.out.println("Error - cant perform a read operation before the previous read operation.");
            return (new ArrayList<>());
        }
        ArrayList<Post> inPost = p.pub.postList;
        ArrayList<Reply> inReply = p.pub.replyList;
        for(int i=0; i<inPost.size(); i++)
        {
            int ts = user.getSubWithID(inPost.get(i).uid).time;
            if(inPost.get(i).time<=t && inPost.get(i).time>=Math.max(lastRead,ts) && user.containsSubWithID(inPost.get(i).uid) && user.getSubWithID(inPost.get(i).uid).status)
            {
                outPost.add(inPost.get(i));
            }
        }

        for(int i=0; i<inReply.size(); i++)
        {
            int ts = user.getSubWithID(inReply.get(i).uid).time;
            if(inReply.get(i).time<=t && inReply.get(i).time>=Math.max(lastRead,ts) && user.containsSubWithID(inReply.get(i).uid) && user.getSubWithID(inReply.get(i).uid).status)
            {
                outReply.add(inReply.get(i));
            }
            else if(inReply.get(i).time<=t && inReply.get(i).time>=lastRead && (inReply.get(i).post.uid.equalsIgnoreCase(user.uid) || inReply.get(i).reply.uid.equalsIgnoreCase(user.uid)))
            {
                outReply.add(inReply.get(i));
            }
        }

        ArrayList<Tuple> sortedRead = new ArrayList<>();
        
        for(int i=0; i<outReply.size(); i++)
        {
            Tuple tuple = new Tuple();
            tuple.text=outReply.get(i).text;
            tuple.time=outReply.get(i).time;
            sortedRead.add(tuple);
        }

        for(int i=0; i<outPost.size(); i++)
        {
            Tuple tuple = new Tuple();
            tuple.text=outPost.get(i).post;
            tuple.time=outPost.get(i).time;
            sortedRead.add(tuple);
        }

        Collections.sort(sortedRead, new Sortbytime()); 
        return sortedRead;
    }
}