

db.createUser(
  {
    user: "jack",
    pwd: "1313",
    roles:
    [
      {
        role: "userAdminAnyDatabase",
        db: "admin"
      }
    ]
  }
)


db.runCommand(
  {
    usersInfo:"jack",
    showPrivileges:true
  }
)






use doctorapp

mongo  127.0.0.1/doctorapp -u jack -p 1313


db.createUser(
  {
    user:"jack",
    pwd:"1313",
    roles:[
      {role:"userAdminAnyDatabase",db:"doctorapp"}
    ]
  }
)


show dbs
show collections





db.userslocation.insert(
   {
      loc : { type: "Point", coordinates: [120, 30 ] },
      userid: 1,
      usertype : 1
   }
)


--用户表

db.users.insert({
username : "jack",
realname : "王小明",
password:"1"
})


--消息表维护 (1 doctor,0 patient)

db.messages.insert(
   {
	      fromid: "551b4cb83b83719a9aba9c01",
        toid:"551b4e1d31ad8b836c655377",
        fromtype:1,
        totype:1,
        msgtime:new Date(),
        content:"hello jack",
        isread:false
   }
)












