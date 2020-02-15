import java.util.ArrayList;

public class Platform
{
    Publish pub = new Publish();
    ArrayList<User> userBase = new ArrayList<>();

    public User getUserWithID(String s)
    {
        for(int i=0;i<userBase.size();i++)
        {
            if(userBase.get(i).uid.equals(s))
                return userBase.get(i);
        }
        return (new User());
    }

    public boolean containsUserWithID(String s)
    {
        for(int i=0;i<userBase.size();i++)
        {
            if(userBase.get(i).uid.equals(s))
                return true;
        }
        return false;
    }

    public void socialPlatform(String actionMessage)
    {
        String arr[]=actionMessage.split(",");

        if(arr[0].equals("PUBLISH"))
        {
            // System.out.println(actionMessage);
            // check for non  unique text ids
            if(arr[3].equals("NEW"))
            {
                if(pub.containsPostWithTID(arr[5]) || pub.containsReplyWithTID(arr[5]))
                {
                    System.out.println("Can't Publish with TID "+arr[5]);
                    return;
                }
                Post post=new Post(Integer.parseInt(arr[1]), arr[2], arr[5], arr[4]);
                pub.addPost(post);
                User user=new User(arr[2]);
                if(!userBase.contains(user))
                    userBase.add(user);
                System.out.println(actionMessage);
            }

            else if (arr[3].substring(0,5).equals("REPLY"))
            {
                if(pub.containsPostWithTID(arr[5]) || pub.containsReplyWithTID(arr[5]))
                {
                    System.out.println("Can't Publish with TID "+arr[5]);
                    return;
                }
                Post parentPost=pub.getPostWithTID(arr[3].substring(6,arr[3].length()-1)); 
                if(parentPost.time==-1)
                {
                    Reply parentReply=pub.getReplyWithTID(arr[3].substring(6,arr[3].length()-1)); 
                    if(parentReply.text.length()<1)
                    {
                        System.out.println("Error - no Post with ID "+arr[3].substring(6,arr[3].length()-1)+" exists for reply with ID "+arr[5]+" to happen.");
                        return;
                    }
                    else 
                    {
                        if(parentReply.time>Integer.parseInt(arr[1]))
                        {
                            System.out.println("Error - you cant Reply to a Post before the Post was published.");
                            return;
                        }
                        Reply reply=new Reply(Integer.parseInt(arr[1]), arr[2], parentReply, arr[5], arr[4], "replyreply");
                        pub.addReply(reply);
                    }
                }
                else 
                {
                    if(parentPost.time>Integer.parseInt(arr[1]))
                    {
                        System.out.println("Error - you cant Reply to a Post before the Post was published.");
                        return;
                    }
                    Reply reply=new Reply(Integer.parseInt(arr[1]), arr[2], parentPost, arr[5], arr[4]);
                    pub.addReply(reply);
                    User user=new User(arr[2]);
                    if(!userBase.contains(user))
                        userBase.add(user);
                }
                System.out.println(actionMessage);
            }

            else if (arr[3].substring(0,6).equals("REPOST"))
            {
                //System.out.println(actionMessage);
                if(pub.containsPostWithTID(arr[4]) || pub.containsReplyWithTID(arr[4]))
                {
                    System.out.println("Can't Publish with TID "+arr[4]);
                    return;
                }
                Post parentPost=pub.getPostWithTID(arr[3].substring(7,arr[3].length()-1));
                if(!(parentPost.post.length()>0))
                {
                    System.out.println("Error - no Post with ID "+arr[3].substring(7,arr[3].length()-1)+" exists for repost with ID "+arr[4]+" to happen.");
                    return;
                }
                else if(parentPost.time>Integer.parseInt(arr[1]))
                {
                    System.out.println("Error - you cant Repost a Post before the Post was published.");
                    return;
                }
                Post repost=new Post(Integer.parseInt(arr[1]), arr[2], arr[4], arr[3].substring(7,arr[3].length()-1), "YES");
                pub.addPost(repost);
                User user=new User(arr[2]);
                if(!userBase.contains(user))
                    userBase.add(user);
                System.out.println(actionMessage);
            }

            else
            {
                System.out.println("Invalid Input");
                return;
            }
        }

        else if(arr[0].equals("SUBSCRIBE"))
        {
            if(arr.length!=4)
            {
                System.out.println("Invalid Input");
                return;
            }
            User user=new User(arr[2]);
            if(!containsUserWithID(arr[2]))
            {
                user.addSub(arr[3], Integer.parseInt(arr[1]));
                userBase.add(user);
            }
            else
            {
                user=getUserWithID(arr[2]);
                user.addSub(arr[3], Integer.parseInt(arr[1]));
                userBase.add(user);
            }
            System.out.println(actionMessage);
        }

        else if(arr[0].equals("UNSUBSCRIBE"))
        {
            if(arr.length!=4)
            {
                System.out.println("Invalid Input");
                return;
            }
            User user=getUserWithID(arr[2]);
            if(user.uid.length()<1)
            {
                System.out.println("Error - Specified User doesnt exist in database to unsubscribe from "+arr[3]);
                return;
            }
            boolean flag=user.removeSub(arr[3], Integer.parseInt(arr[1]));
            if(flag)
                System.out.println(actionMessage);
        }

        else if(arr[0].equals("READ"))
        {
            if(arr.length!=3)
            {
                System.out.println("Invalid Input");
                return;
            }
            User user=getUserWithID(arr[2]);
            if(user.uid.length()<1)
            {
                System.out.println("Error - Specified User doesnt exist in database.");
                return;
            }
            ArrayList<Read.Tuple> sortedRead = user.read.performReadAtTime(Integer.parseInt(arr[1]), this, user);
            //include replies reposts of subscribed publisher
            //if user reads, unsubs, subs again, and read again, show everything from t=new subscribe time
            if(sortedRead.size()<=0)
            {
                System.out.println("No text available for uid "+arr[2]);
                return;
            }
            System.out.print(actionMessage+",[");
            String s="";
            for(int i=0; i<sortedRead.size(); i++)
            {
                s+=sortedRead.get(i).text+",";
            }
            System.out.println(s.substring(0, s.length()-1)+"]");
        }

        else
        {
            System.out.println("Invalid Input");
        }
    }

}