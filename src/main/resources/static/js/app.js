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
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error("Lỗi khi gọi GraphQL:", error);
        return { errors: [{ message: "Không thể kết nối đến máy chủ." }] };
    }
}

/**
 * Hàm render danh sách sản phẩm lên bảng
 * @param {Array} products - Mảng các đối tượng sản phẩm
 */
function renderProducts(products) {
    const tableBody = document.getElementById('product-table-body');
    tableBody.innerHTML = ''; // Xóa dữ liệu cũ

    if (!products || products.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="4">Không có sản phẩm nào.</td></tr>`;
        return;
    }

    products.forEach(product => {
        const row = `<tr>
            <td>${product.id}</td>
            <td>${product.title}</td>
            <td>${product.price}</td>
            <td>${product.desc || ''}</td>
        </tr>`;
        tableBody.innerHTML += row;
    });
}

/**
 * Hàm lấy tất cả sản phẩm (sắp xếp theo giá) và render
 */
async function fetchAllProducts() {
    const query = `
        query GetProductsSorted {
            productsSortedByPrice(sortDirection: "ASC") {
                id
                title
                price
                desc
            }
        }
    `;
    const result = await fetchGraphQL(query);
    if (result.data && result.data.productsSortedByPrice) {
        renderProducts(result.data.productsSortedByPrice);
    } else {
        renderProducts([]); // Render bảng rỗng nếu có lỗi
    }
}

/**
 * Hàm lấy sản phẩm theo Category và render
 * @param {string} categoryId - ID của category cần lọc
 */
async function fetchProductsByCategory(categoryId) {
    const query = `
        query GetProductsByCategory($catId: ID!) {
            productsByCategory(categoryId: $catId) {
                id
                title
                price
                desc
            }
        }
    `;
    const variables = { catId: categoryId };
    const result = await fetchGraphQL(query, variables);
    if (result.data && result.data.productsByCategory) {
        renderProducts(result.data.productsByCategory);
    } else {
        renderProducts([]); // Render bảng rỗng nếu có lỗi
    }
}

// --- GẮN CÁC SỰ KIỆN KHI TRANG TẢI XONG ---
document.addEventListener('DOMContentLoaded', () => {
    // 1. Tải danh sách sản phẩm ban đầu
    fetchAllProducts();

    // 2. Sự kiện cho nút Lọc sản phẩm
    document.getElementById('filter-btn').addEventListener('click', () => {
        const categoryId = document.getElementById('filterCategoryId').value;
        if (categoryId) {
            fetchProductsByCategory(categoryId);
        }
    });

    // 3. Sự kiện cho nút Reset Filter
    document.getElementById('reset-filter-btn').addEventListener('click', () => {
        document.getElementById('filterCategoryId').value = '';
        fetchAllProducts();
    });

    // 4. Sự kiện Form Thêm sản phẩm
    document.getElementById('add-product-form').addEventListener('submit', async function(event) {
        event.preventDefault();
        const variables = {
            product: {
                title: document.getElementById('title').value,
                price: parseFloat(document.getElementById('price').value),
                desc: document.getElementById('desc').value,
                quantity: parseInt(document.getElementById('quantity').value, 10) || 0,
                userId: document.getElementById('userId').value
            }
        };
        const mutation = `
            mutation CreateProduct($product: ProductInput!) {
                createProduct(product: $product) { id title }
            }`;
        const result = await fetchGraphQL(mutation, variables);
        const resultDiv = document.getElementById('add-product-result');
        if (result.data?.createProduct) {
            resultDiv.innerHTML = `<p style="color: green;">Thêm thành công sản phẩm: ${result.data.createProduct.title}</p>`;
            fetchAllProducts();
            this.reset();
        } else {
            resultDiv.innerHTML = `<p style="color: red;">Lỗi: ${result.errors[0].message}</p>`;
        }
    });

    // 5. Sự kiện Form Xóa sản phẩm
    document.getElementById('delete-product-form').addEventListener('submit', async function(event) {
        event.preventDefault();
        const productId = document.getElementById('deleteProductId').value;
        const mutation = `mutation DeleteProduct($id: ID!) { deleteProduct(id: $id) }`;
        const result = await fetchGraphQL(mutation, { id: productId });
        const resultDiv = document.getElementById('delete-product-result');
        if (result.data?.deleteProduct === true) {
            resultDiv.innerHTML = `<p style="color: green;">Đã xóa thành công sản phẩm ID: ${productId}</p>`;
            fetchAllProducts();
            this.reset();
        } else {
            resultDiv.innerHTML = `<p style="color: red;">Lỗi: Không thể xóa sản phẩm hoặc ID không tồn tại.</p>`;
        }
    });
    
    // 6. Sự kiện Form Sửa User
    document.getElementById('update-user-form').addEventListener('submit', async function(event) {
        event.preventDefault();
        const userId = document.getElementById('updateUserId').value;
        const variables = {
            id: userId,
            user: {
                fullname: document.getElementById('updateFullname').value,
                email: document.getElementById('updateEmail').value,
                // Để đơn giản, không cập nhật password ở đây
                password: "defaultpassword",
                phone: ""
            }
        };
        const mutation = `
            mutation UpdateUser($id: ID!, $user: UserInput!) {
                updateUser(id: $id, user: $user) { id fullname }
            }`;
        const result = await fetchGraphQL(mutation, variables);
        const resultDiv = document.getElementById('update-user-result');
        if (result.data?.updateUser) {
            resultDiv.innerHTML = `<p style="color: green;">Cập nhật thành công User: ${result.data.updateUser.fullname}</p>`;
            this.reset();
        } else {
            resultDiv.innerHTML = `<p style="color: red;">Lỗi: ${result.errors[0].message}</p>`;
        }
    });

    // 7. Sự kiện Form Xóa User
    document.getElementById('delete-user-form').addEventListener('submit', async function(event) {
        event.preventDefault();
        const userId = document.getElementById('deleteUserId').value;
        const mutation = `mutation DeleteUser($id: ID!) { deleteUser(id: $id) }`;
        const result = await fetchGraphQL(mutation, { id: userId });
        const resultDiv = document.getElementById('delete-user-result');
        if (result.data?.deleteUser === true) {
            resultDiv.innerHTML = `<p style="color: green;">Đã xóa thành công User ID: ${userId}</p>`;
            this.reset();
        } else {
            resultDiv.innerHTML = `<p style="color: red;">Lỗi: Không thể xóa User hoặc ID không tồn tại.</p>`;
        }
    });

    // 8. Sự kiện Form Sửa Category
    document.getElementById('update-category-form').addEventListener('submit', async function(event) {
        event.preventDefault();
        const categoryId = document.getElementById('updateCategoryId').value;
        const variables = {
            id: categoryId,
            category: {
                name: document.getElementById('updateCategoryName').value
            }
        };
        const mutation = `
            mutation UpdateCategory($id: ID!, $category: CategoryInput!) {
                updateCategory(id: $id, category: $category) { id name }
            }`;
        const result = await fetchGraphQL(mutation, variables);
        const resultDiv = document.getElementById('update-category-result');
        if (result.data?.updateCategory) {
            resultDiv.innerHTML = `<p style="color: green;">Cập nhật thành công Category: ${result.data.updateCategory.name}</p>`;
            this.reset();
        } else {
            resultDiv.innerHTML = `<p style="color: red;">Lỗi: ${result.errors[0].message}</p>`;
        }
    });

    // 9. Sự kiện Form Xóa Category
    document.getElementById('delete-category-form').addEventListener('submit', async function(event) {
        event.preventDefault();
        const categoryId = document.getElementById('deleteCategoryId').value;
        const mutation = `mutation DeleteCategory($id: ID!) { deleteCategory(id: $id) }`;
        const result = await fetchGraphQL(mutation, { id: categoryId });
        const resultDiv = document.getElementById('delete-category-result');
        if (result.data?.deleteCategory === true) {
            resultDiv.innerHTML = `<p style="color: green;">Đã xóa thành công Category ID: ${categoryId}</p>`;
            this.reset();
        } else {
            resultDiv.innerHTML = `<p style="color: red;">Lỗi: Không thể xóa Category hoặc ID không tồn tại.</p>`;
        }
    });
});