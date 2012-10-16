$(document).ready(function(){
  
  var reverse_study = false;
  var magic7_study = false;
  
  $.getJSON('./data',function(data){
    $.each(data.items,function(index,word){
      var html = '<li class="ui-state-default liststudy" mean="'+
              word.mn+'" wordid="'+word.id+'" box="'+word.bx+'">'+word.wd+'</li>';
      $('#card_box')
        .append(
          $(html).bind('click',listclick)
        );
    });
    init();
  }).error(function() { 
    alert("Error happened when you get some data."); 
  });  
  
  $('#reverse_study').change(function(){
    reverse_study = $(this).is(":checked");
  });
  
  $('#magic7_study').change(function(){
    magic7_study = $(this).is(":checked");
  });
  
  function card_flip(event){
    var card = $('.ui-state-default[wordid='+$(this).attr('wordid')+']');
    if($(this).attr('toggle') == '0'){
      $(this).attr('toggle','1');
      if(!reverse_study){
        $(this).text(card.attr('mean'));
      }else{
        $(this).text(card.text());
      }
    }else{
      $(this).attr('toggle','0');
      if(!reverse_study){
        $(this).text(card.text());
      }else{
        $(this).text(card.attr('mean'));
      }
    }
  };

  function listclick(){
    var card = $(this);

    $("#dlg_wordid").val(card.attr('wordid'));
    $("#dlg_word").val(card.html());
    $("#dlg_mean").val(card.attr('mean'));
    
    $('#modify_dialog').dialog({
      title: card.html(),
      width: '400',
      height: '280',
      resizable : false,
      buttons:{OK:function(){
        var wordid = $("#dlg_wordid").val();
        var card = $('.ui-state-default[wordid='+wordid+']');
        card.attr('mean',$("#dlg_mean").val());
        card.html($("#dlg_word").val());
        
        //서버 데이타 호출 로직 추가.
                
        $(this).dialog('close');
      },Cancel:function(){
        $(this).dialog('close');
      }}
    });
  };

  function init(){
    var card = $('.ui-state-default.liststudy:first');
    
    if(!reverse_study){
      $('#card').text(card.text());
    }else{
      $('#card').text(card.attr('mean'));
    }
  
    $('#card')
      .hide()
      .attr('wordid',card.attr('wordid'))
      .fadeIn()
      .click(card_flip)
      .draggable({
        axis: 'y',
        revert:true,
        revertDuration:0,
        stop:function(e){
        
          $(this).attr('toggle','0').removeClass('drag');;
          if(card.text() != ''){
            if(!reverse_study){
              $(this).hide().text(card.text());
            }else{
              $(this).hide().text(card.attr('mean'));
            }
            $(this).fadeIn(function(){
              $(this).bind('click',card_flip);
            });
          }else{
            $(this).hide().text('').fadeIn();
          }
          
        },
        start:function(){
          if(!check_card_validate()) return false;       
          $(this).unbind('click').addClass('drag');
        }
      });

    
    var droppable_drop_out = function(event,ui,obj){
      if(obj == null) obj = $(this);
      if(obj.attr('id') == 'yes_ans'){
        obj.removeClass('border_yes');
      }else if(obj.attr('id') == 'no_ans'){
        obj.removeClass('border_no');
      }else{
        obj.removeClass('border_sure');
      }      
    };
    
    var droppable_drop_over = function(event,ui,obj){
      if(!check_card_validate()) return;
             
      if(obj == null) obj = $(this);
      if(obj.attr('id') == 'yes_ans'){
        obj.addClass('border_yes');
      }else if(obj.attr('id') == 'no_ans'){
        obj.addClass('border_no');
      }else{
        obj.addClass('border_sure');
      }
    };
    
    var check_card_validate = function(){
      if(card.attr('wordid') == null ||
         card.attr('wordid') == '' ||
         card.attr('wordid') == 'undefined') return false;
      return true;
    };
    
    $('#yes_ans, #no_ans, #sure_ans')
      .droppable({
        drop:function(event,ui){
          droppable_drop_out(event,ui,$(this));
          
          if(!check_card_validate()) return;
          
          $(this).find("span.title")
            .after(card.clone().removeClass("liststudy")
                      .addClass("completestudy")
                      .bind('click',listclick).hide().fadeIn('fast'));
          
          var boxobj = null;
          if($(this).attr('id') == 'yes_ans'){
            boxobj = $("#"+(parseInt(card.attr('box'))+1)+"_box");
          }else if($(this).attr('id') == 'no_ans'){
            boxobj = $("#1_box");
          }else{
            boxobj = $("#6_box");
          }

          boxobj.animate({borderColor:"green"},500).animate({borderColor:"#CDCDCD"},500);
          
          card.remove();
          card = $('.ui-state-default.liststudy:first');
          $('#card').attr('wordid',card.attr('wordid'));
          
          //서버 처리.
          
        },
        tolerance: 'pointer',
        over : droppable_drop_over,
        out : droppable_drop_out
      });
  }
});