
//check if repost is after post
public class Post
{

    String uid; // publisher's ID
    String tid; // text post ID
    String post; // actual post
    int time;
    boolean isRepost;
    String opID; //original post ID

    public Post()
    {
        uid="";
        tid="";
        post="";
        time=-1;
        isRepost=false;
        opID="";
    }

    public Post(int time, String uid, String tid, String post)
    {
        this.uid=uid;
        this.tid=tid;
        this.isRepost=false;
        opID="";
        if(post.length()>30) 
        {
            System.out.println("Error - post cannot have more than 30 characters.");
            this.post="";
            this.time=-1;
        }
        else
        {
            this.post=post;
            if(time<0)
            {
                System.out.println("Error - time cannot be negative.");
                this.time=-1;
            }
            else
                this.time=time;
        }
    }

    public Post(int time, String uid, String tid, String opID, String isrepost)
    {
        this.uid=uid;
        this.tid=tid;
        this.isRepost=true;
        this.opID=opID;
        if(time<0)
        {
            System.out.println("Error - time cannot be negative.");
            this.time=-1;
        }
        else
            this.time=time;
    }


}