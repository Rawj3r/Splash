<?php
require_once'test.php';
function user(){
  global $db;

      $id=0;
      $name='';
      $hold=array();
      if ($v =$db->prepare("CALL prGet_User_Profile(?)")) {

          if ($v->bind_param('s',$username)) {

              if($v->execute()){


                  $v->bind_result($id,$name,$email,$pass);


                  while($v->fetch()){

                      $hold=[$id,$name,$email,$pass];

                  }


                  if(count($hold)>1){


                      return [true,$hold];
                  }else{


                      return [false,"Incorrect"];

                  }


              }
              else
              {
                  return [false,json_encode("Error in Login-execute statement")];

              }

          }
          else
          {

              return [false,json_encode("Error in Login-binding statement")];
          }


      }
      else{
          return [false,json_encode("Error in Login-prepare stastment")];
      }

  }
  $roger = user();