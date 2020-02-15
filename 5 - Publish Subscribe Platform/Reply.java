//check if reply is after post
public class Reply
{

    String uid; // publisher's ID
    String tid; //reply textID
    String parentID; // text post/reply/repost ID to which reply is given
    String text; // actual reply
    Post post; // parent post
    Reply reply; // parent post
    int time;

    public Reply()
    {
        uid="";
        tid="";
        text="";
        parentID="";
        time=-1;
        post=new Post();
    }

    public Reply(int time, String uid, Post post, String tid, String text)
    {
        reply=new Reply();
        this.uid=uid;
        this.post=post;
        this.tid=tid;
        this.parentID=post.tid;
        if(text.length()>30) 
        {
            System.out.println("Error - replies cannot have more than 30 characters.");
            this.text="";
            this.time=-1;
        }
        else
        {
            this.text=text;
            if(time<0)
            {
                System.out.println("Error - time cannot be negative.");
                this.time=-1;
            }
            else
                this.time=time;
        }
    }

    public Reply(int time, String uid, Reply reply, String tid, String text, String isreplytoreply)
    {
        this.uid=uid;
        this.reply=reply;
        this.tid=tid;
        this.parentID=reply.tid;
        post=new Post();
        if(text.length()>30) 
        {
            System.out.println("Error - replies cannot have more than 30 characters.");
            this.text="";
            this.time=-1;
        }
        else
        {
            this.text=text;
            if(time<0)
            {
                System.out.println("Error - time cannot be negative.");
                this.time=-1;
            }
            else
                this.time=time;
        }
    }

}