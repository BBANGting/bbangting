/* 비밀번호 유효성 검사 */
$('#password').on('propertychange change keyup paste input focusout', function(){
    const regexp = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}$/;
    const password = $('[name=password]').val();

    if(password != '') {
        /* 1. 영문, 숫자, 특수기호 포함 여부 검사 */
        if(!regexp.test(password)) {
            $('#checkPwdMsg').text('8~16자의 영문 대소문자/숫자/특수기호 조합하여 입력하세요');
            $('#registBtn').attr('disabled', true);
        } else {
            $('#checkPwdMsg').text('');
            $('#registBtn').attr('disabled', false);
        }

        /* 2. 아이디와 연속 일치 여부 검사 */
        if(isValidPwd()) {
            $('#checkPwdMsg').text('아이디와 연속 3자리 이상 일치하는 비밀번호는 사용할 수 없어요');
            $('#registBtn').attr('disabled', true);
        }
    } else {
        $('#checkPwdMsg').text('비밀번호는 필수 입력 항목입니다');
        $('#registBtn').attr('disabled', true);
    }
});

function isValidPwd() {
    let id = $('[name=email]').val();
    let pwd = $('[name=password]').val();

    let tmp = '';
    let count = 0;

    for(i=0; i < id.length-2; i++) {
        tmp = id.charAt(i) + id.charAt(i+1) + id.charAt(i+2);
        if(pwd.indexOf(tmp) > -1) { count = count + 1 };
    }
    return count > 0 ? true : false;
}

/* 비밀번호 입력란과 확인란 일치 여부 확인 */
$('#confirmPwd').on('propertychange change keyup paste input', function(){
    if($('#password').val() != $(this).val()) {
        $('#confirmPwdMsg').text('비밀번호가 일치하지 않습니다');
        $('#registBtn').attr('disabled', true);
    } else {
        $('#confirmPwdMsg').text('');
        $('#registBtn').attr('disabled', false);
    }
});

/* 이메일 유효성 검사 */
$('#email').on('propertychange change keyup paste input focusout', function(){    const regexp = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    const email = $('[name=email]').val();

    if(email != '') {
        $.ajax({
            url : '/user/checkEmail',
            type : 'post',
            data : {'email' : email},
            success : function(result){
                console.log(result);
                if(result > 0) {
                    $('#checkEmailMsg').text('이미 가입된 이메일입니다');
                    $('#registBtn').attr('disabled', true);
                } else {
                    if(!regexp.test(email)) {
                        $('#checkEmailMsg').text('이메일 형식이 올바르지 않습니다');
                        $('#registBtn').attr('disabled', true);
                    } else {
                        $('#checkEmailMsg').text('');
                        $('#registBtn').attr('disabled', false);
                    }
                }
            },
            error : function(status, error){ console.log(status, error); }
        });

    } else {
        $('#checkEmailMsg').text('이메일은 필수 입력 항목입니다');
        $('#registBtn').attr('disabled', true);
    }
})