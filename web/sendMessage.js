var xhr = new XMLHttpRequest();
var mTimer;
var timer = 3000;

function getChatMessages() {
    if(xhr.readyState == 4) {
        var nickname = document.getElementById("nick").value;
        var x = xhr.responseText;
        obj = JSON.parse(x);
        var tamanhoDoArray = obj.length;
        var linha = "<tr><td>";
        for (var i = 0; i < tamanhoDoArray; i++){
            var item = obj[i];
            var time = item.timeStamp;
            var data = new Date(time);
            h=data.getHours();
            m=data.getMinutes();
            s=data.getSeconds();
            
            if(item.chatterName == nickname){                
                linha += '<font face=\"Arial\" size=\"2\" color=\"blue\"><b>'+ item.chatterName + ' ('+ h+':'+m+':'+s + ') &gt; </b></font>' + item.message + '</td></tr><tr><td>';  
            }else if(item.chatterName == "ESSEChat"){
                linha += '<font face=\"Arial\" size=\"2\" color=\"red\">' + item.message + '</span>' + '</td></tr><tr><td>';  
            }else{
                linha += '<font face=\"Arial\" size=\"2\"><b>'+ item.chatterName + ' ('+ h+':'+m+':'+s + ') &gt; </b></font>' + item.message + '</td></tr><tr><td>';  
            }
            var output = document.getElementById("tbmsgs");
            output.innerHTML = linha;
        }
         
        mTimer = setTimeout ("atualizar()", timer);
    }
}

function atualizaChat(){
    if(xhr.readyState == 4) {
        var nickname = document.getElementById("nick").value;
        var x = xhr.responseText;
        obj = JSON.parse(x);
        var n = obj[0];
        var logados = n.logados;
        var cabecalho = document.getElementById("tbusers");
        //atualiza lista de usuarios logados
        cabecalho.innerHTML = '<tr><th><span class=\"white\">Usuários nesta sala: '+logados+ '</span></th></tr>';
        
        var linha = "<tr><td><font face=\"Arial\" size=\"2\" color=\"blue\">"+ nickname + "</font></tr></td>";
                
        for (var i = 1; i <=logados; i++){
            var item = obj[i];  
            if(item.chatterName != nickname){                
                linha += "<tr><td><font face=\"Arial\" size=\"2\"><a href=\"javascript:winopen('viewinfo.jsp?chatteriD="+item.chatterId+"')\" title=\"Veja informações sobre" + " " + item.chatterName+"\">"+item.chatterName+"</a>"+ " " + item.status+"</font></tr></td>";
            }
            var corpo = document.getElementById("tbodyusers");
            corpo.innerHTML = linha;
        }
        
        //atualiza lista de mensagens
        var tamanhoDoArray = obj.length;
        linha = "<tr><td>";
        var inicio = logados+1;
        for (var i = inicio; i < tamanhoDoArray; i++){
            var item = obj[i];
            var time = item.timeStamp;
            var data = new Date(time);
            h=data.getHours();
            m=data.getMinutes();
            s=data.getSeconds();
            
            if(item.chatterName == nickname){                
                linha += '<font face=\"Arial\" size=\"2\" color=\"blue\"><b>'+ item.chatterName + ' ('+ h+':'+m+':'+s + ') &gt; </b></font>' + item.message + '</td></tr><tr><td>'; 
            }else if(item.chatterName == "ESSEChat"){
                linha += '<font face=\"Arial\" size=\"2\" color=\"red\">' + item.message + '</span>' + '</td></tr><tr><td>';  
            }else{
                linha += '<font face=\"Arial\" size=\"2\"><b>'+ item.chatterName + ' ('+ h+':'+m+':'+s + ') &gt; </b></font>' + item.message + '</td></tr><tr><td>';  
            }
            var output = document.getElementById("tbmsgs");
            output.innerHTML = linha;
        }
         
        mTimer = setTimeout ("atualizar()", timer);
        

    }
}

function atualizar() {
    clearInterval (mTimer);
    xhr.open("get", "AjaxSendMessage?t=" + Math.random(), true);
    xhr.onreadystatechange = atualizaChat;
    xhr.send();
}

function iniciarChat(){
    var formulario = document.getElementById("send");
    formulario.onsubmit = enviar;
    var formulario = document.getElementById("up");
    formulario.onchange = subir;
    
    document.getElementById("message").focus();
    mTimer = setTimeout ("atualizar()", timer);
    atualizar();
}

function limpar(){
    document.getElementById("message").value = "";
    document.getElementById("message").focus();
    return false;
}

function enviar(evt){
    evt.preventDefault();
    clearInterval (mTimer);
    var y = document.getElementById("send");
    var msg = y.messagebox.value;
    if(msg==""){
        alert("Você não escreveu uma mensagem para envio!");
    }else{
        var params = "message=" + msg;
//    params += "&dataNascimento=" + encodeURIComponent(data);
//    params += "&salario=" + encodeURIComponent(salario);    
        xhr.open("post", "AjaxSendMessage", true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.setRequestHeader("Content-length", params.length);
        xhr.onreadystatechange = getChatMessages;
        xhr.send(params);
        limpar();
//        return false;//impede que os dados sejam submetidos novamente (como o prevenDefault);
    }
}

function subir(){
    
    clearInterval (mTimer);
    var len = this.files.length, formdata = new FormData();

    for (var i = 0; i < len; i++) {
        var file = this.files[i];
        var tipo = file.type;

        if (tipo.match(/image.*/) || tipo.match(/application.*/)) {
            var reader = new FileReader();

            reader.readAsDataURL(file);
            formdata.append("images[]", file);
        }
    }
    xhr.open("post", "Upload", true);
    xhr.onreadystatechange = uploadOk;
    xhr.send(formdata);
    document.getElementById("formup").reset();
    return false;
}

function uploadOk() {
        if (xhr.readyState === 4) {
            var ok = xhr.responseText;
            alert(ok);
            mTimer = setTimeout ("atualizar()", timer);
        }
 }
        
onload = iniciarChat;