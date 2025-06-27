$(document).ready(function () {
    const API_BASE = "/product";
    //console.log("Token lấy từ localStorage:", getToken());
    function getToken(){
        return localStorage.getItem("jwtToken");
    }
    const token = localStorage.getItem("jwtToken");
    if (!getToken()) {
        alert("Bạn chưa đăng nhập hoặc token hết hạn. Vui lòng đăng nhập lại.");
        window.location.href = "login.html";
        return;
    }
    function getAuthHeaders(){
        const token = getToken();
        return token ? {
            "Authorization": "Bearer " + token
        } : {};

    }

function filterPrice(priceMin,  priceMax) {
    $.ajax({
        url: API_BASE + `/filterByPrice?priceMin=${priceMin}&priceMax=${priceMax}`,
        method: "GET",
        headers: getAuthHeaders(),
        success: function(data){
            let html = "";
            data.forEach(element => {
                html += `<tr>
                    <td>${element.id}</td>
                    <td>${element.category}</td>
                    <td>${element.name}</td>
                    <td>${element.price}</td>
                    <td>
                      <button class="deleteBtn" data-id="${element.id}">Xoá</button>
                      <button class="loadBtn" data-id="${element.id}">Chỉnh sửa</button>
                    </td>
                  </tr>`;
            });
            $("#productTable tbody").html(html);
        },
        error: function(error){
            if(error.status == 404){
                $("#productTable tbody").html("<tr><td colspan='5'>Không tìm thấy sản phẩm phù hợp.</td></tr>");
            }else
                alert("Lỗi khi nhập giá");
        }
    });
}

$("#filterBtn").on("click", function (e) {
    e.preventDefault();
    const minPrice = $("#minPrice").val().trim();
    const maxPrice = $("#maxPrice").val().trim();
    if (parseFloat(minPrice) > parseFloat(maxPrice)) {
        alert("Giá tối thiểu không được lớn hơn giá tối đa.");
        return;
    }
    filterPrice(minPrice, maxPrice);

});

});