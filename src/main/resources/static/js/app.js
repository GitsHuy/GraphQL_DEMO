const API_URL = '/graphql';

/**
 * Hàm chung để gửi yêu cầu đến GraphQL API
 * @param {string} query - Chuỗi truy vấn GraphQL
 * @param {object} variables - Các biến cho truy vấn (nếu có)
 * @returns {Promise<any>}
 */
async function fetchGraphQL(query, variables = {}) {
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ query, variables })
        });
        if (!response.ok) {
            const errorBody = await response.json();
            throw new Error(errorBody.errors[0].message || `HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error("Lỗi khi gọi GraphQL:", error);
        return { errors: [{ message: error.message || "Không thể kết nối đến máy chủ." }] };
    }
}

/**
 * Hiển thị thông báo kết quả
 * @param {HTMLElement} element - Div hiển thị kết quả
 * @param {string} message - Nội dung thông báo
 * @param {boolean} isSuccess - true nếu là thành công, false nếu là lỗi
 */
function showResult(element, message, isSuccess) {
    element.textContent = message;
    element.style.color = isSuccess ? 'green' : 'red';
    element.style.backgroundColor = isSuccess ? '#e9f7ef' : '#fbe9e9';
    element.style.border = `1px solid ${isSuccess ? 'green' : 'red'}`;
}

/**
 * Render danh sách sản phẩm lên bảng
 * @param {Array} products - Mảng các đối tượng sản phẩm
 */
function renderProducts(products) {
    const tableBody = document.getElementById('product-table-body');
    tableBody.innerHTML = '';

    if (!products || products.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="5">Không có sản phẩm nào.</td></tr>`;
        return;
    }

    products.forEach(product => {
        const row = `<tr>
            <td>${product.id}</td>
            <td>${product.title}</td>
            <td>${product.price}</td>
            <td>${product.category?.name || 'N/A'}</td>
            <td>${product.user?.fullname || 'N/A'}</td>
        </tr>`;
        tableBody.innerHTML += row;
    });
}

/**
 * Lấy tất cả sản phẩm (sắp xếp theo giá) và render
 */
async function fetchAllProducts() {
    const query = `
        query GetProductsSorted {
            productsSortedByPrice(sortDirection: "ASC") {
                id title price 
                category { name }
                user { fullname }
            }
        }`;
    const result = await fetchGraphQL(query);
    renderProducts(result.data?.productsSortedByPrice);
}

/**
 * Lấy sản phẩm theo Category và render
 * @param {string} categoryId - ID của category cần lọc
 */
async function fetchProductsByCategory(categoryId) {
    const query = `
        query GetProductsByCategory($catId: ID!) {
            productsByCategory(categoryId: $catId) {
                id title price
                category { name }
                user { fullname }
            }
        }`;
    const result = await fetchGraphQL(query, { catId: categoryId });
    renderProducts(result.data?.productsByCategory);
}


// --- GẮN CÁC SỰ KIỆN KHI TRANG TẢI XONG ---
document.addEventListener('DOMContentLoaded', () => {
    fetchAllProducts();

    // --- SỰ KIỆN USER ---
    document.getElementById('add-user-form').addEventListener('submit', async function(event) {
        event.preventDefault();
        const variables = {
            user: {
                fullname: document.getElementById('fullname').value,
                email: document.getElementById('email').value,
                password: document.getElementById('password').value
            }
        };
        const mutation = `mutation CreateUser($user: UserInput!) { createUser(user: $user) { id fullname } }`;
        const result = await fetchGraphQL(mutation, variables);
        const resultDiv = document.getElementById('add-user-result');
        if (result.data?.createUser) {
            showResult(resultDiv, `Thêm thành công User: ${result.data.createUser.fullname} (ID: ${result.data.createUser.id})`, true);
            this.reset();
        } else {
            showResult(resultDiv, `Lỗi: ${result.errors[0].message}`, false);
        }
    });

    // --- SỰ KIỆN CATEGORY ---
    document.getElementById('add-category-form').addEventListener('submit', async function(event) {
        event.preventDefault();
        const variables = { category: { name: document.getElementById('categoryName').value } };
        const mutation = `mutation CreateCategory($category: CategoryInput!) { createCategory(category: $category) { id name } }`;
        const result = await fetchGraphQL(mutation, variables);
        const resultDiv = document.getElementById('add-category-result');
        if (result.data?.createCategory) {
            showResult(resultDiv, `Thêm thành công Category: ${result.data.createCategory.name} (ID: ${result.data.createCategory.id})`, true);
            this.reset();
        } else {
            showResult(resultDiv, `Lỗi: ${result.errors[0].message}`, false);
        }
    });

    // --- SỰ KIỆN SẢN PHẨM ---
    document.getElementById('filter-btn').addEventListener('click', () => {
        const categoryId = document.getElementById('filterCategoryId').value;
        if (categoryId) fetchProductsByCategory(categoryId);
    });

    document.getElementById('reset-filter-btn').addEventListener('click', fetchAllProducts);

    document.getElementById('add-product-form').addEventListener('submit', async function(event) {
        event.preventDefault();
        const variables = {
            product: {
                title: document.getElementById('title').value,
                price: parseFloat(document.getElementById('price').value),
                quantity: parseInt(document.getElementById('quantity').value) || 0,
                userId: document.getElementById('userId').value,
                categoryId: document.getElementById('categoryId').value
            }
        };
        const mutation = `mutation CreateProduct($product: ProductInput!) { createProduct(product: $product) { id title } }`;
        const result = await fetchGraphQL(mutation, variables);
        const resultDiv = document.getElementById('add-product-result');
        if (result.data?.createProduct) {
            showResult(resultDiv, `Thêm thành công sản phẩm: ${result.data.createProduct.title}`, true);
            fetchAllProducts();
            this.reset();
        } else {
            showResult(resultDiv, `Lỗi: ${result.errors[0].message}`, false);
        }
    });
});
