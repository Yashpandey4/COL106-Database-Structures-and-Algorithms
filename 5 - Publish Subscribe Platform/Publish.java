import java.util.ArrayList;

public class Publish 
{
    ArrayList<Post> postList;
    ArrayList<Reply> replyList;
    
    public Publish()
    {
        postList=new ArrayList<Post>();
        replyList=new ArrayList<Reply>();
    }

    public void addPost(Post post)
    {
        postList.add(post);
    }

    public void addReply(Reply reply)
    {
        replyList.add(reply);
    }

    public Post getPostWithTID(String str)
    {
        for(int i=0; i<postList.size(); i++)
        {
            if(postList.get(i).tid.equalsIgnoreCase(str))
            {
                return postList.get(i);
            }
        }
        return (new Post());
    }

    public boolean containsPostWithTID(String str)
    {
        for(int i=0; i<postList.size(); i++)
        {
            if(postList.get(i).tid.equalsIgnoreCase(str))
            {
                return true;
            }
        }
        return false;
    }

    public Reply getReplyWithTID(String str)
    {
        for(int i=0; i<replyList.size(); i++)
        {
            if(replyList.get(i).tid.equalsIgnoreCase(str))
            {
                return replyList.get(i);
            }
        }
        return (new Reply());
    }

    public boolean containsReplyWithTID(String str)
    {
        for(int i=0; i<replyList.size(); i++)
        {
            if(replyList.get(i).tid.equalsIgnoreCase(str))
            {
                return true;
            }
        }
        return false;
    }
    
}