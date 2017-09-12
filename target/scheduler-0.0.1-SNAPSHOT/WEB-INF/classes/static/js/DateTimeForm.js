function getClock()
{
  var today = new Date();
  var dd = today.getDate();
  var mm = today.getMonth()+1; //January is 0!
  var yyyy = today.getFullYear();
  var HH = today.getHours();
  var MM = today.getMinutes();
  var SS = today.getSeconds();

  if(dd<10) 
  {
      dd = '0'+dd
  } 

  if(mm<10) 
  {
      mm = '0'+mm
  } 

  if(HH<10) 
  {
      HH = '0'+HH
  } 

  if(MM<10) 
  {
      MM = '0'+MM
  } 

  if(SS<10) 
  {
      SS = '0'+SS
  } 
  
  today = dd + '.' + mm + '.' + yyyy + ' - ' + HH + ':' + MM + ':' + SS;
  document.getElementById("date").innerHTML = today;
  setTimeout(getClock,1000);
 }

getClock();
