// auth-utils.js

// Lấy token từ localStorage hoặc sessionStorage
function getToken() {
    return localStorage.getItem("jwtToken") || sessionStorage.getItem("jwtToken");
}

// Lưu token vào storage tương ứng (local hoặc session)
function setToken(token) {
    if (localStorage.getItem("jwtToken") !== null) {
        localStorage.setItem("jwtToken", token);
    } else {
        sessionStorage.setItem("jwtToken", token);
    }
}

// Gọi API kèm token, tự động refresh nếu 401
function callApiWithToken(url, method = "GET", body = null, retry = true) {
    const token = getToken();

    return $.ajax({
        url: url,
        method: method,
        contentType: "application/json",
        headers: {
            Authorization: `Bearer ${token}`
        },
        data: body ? JSON.stringify(body) : null
    }).fail(function(xhr) {
        if (xhr.status === 401 && retry) {
            // Token hết hạn, thử refresh token
            return refreshAccessToken().then(() => {
                // Lấy token mới rồi gọi lại API
                return callApiWithToken(url, method, body, false);
            }).catch(() => {
                alert("Phiên đăng nhập hết hạn, vui lòng đăng nhập lại.");
                window.location.href = "/html/login.html";
            });
        } else {
            alert("Lỗi khi gọi API hoặc phiên đăng nhập hết hạn.");
            window.location.href = "/html/login.html";
        }
    });
}

// Gọi API refresh token lấy access token mới
function refreshAccessToken() {
    const refreshToken = getToken(); // Nếu có token riêng refresh, chỉnh lại chỗ này

    return $.ajax({
        url: "/refreshToken",
        method: "POST",
        headers: {
            Authorization: `Bearer ${refreshToken}`
        }
    }).done(function(res) {
        if (res.accessToken) {
            setToken(res.accessToken);
        } else {
            return $.Deferred().reject("Không nhận được access token mới");
        }
    });
}

// Kiểm tra xem có token không, nếu không thì chuyển về login
function checkLogin() {
    if (!getToken()) {
        alert("Bạn chưa đăng nhập hoặc token hết hạn. Vui lòng đăng nhập lại.");
        window.location.href = "/html/login.html";
        return false;
    }
    return true;
}
