blog.column = {
    post() {
        return [
            {
                title: '번호',
                name: 'no',
                width: 50,
                align: 'center',
                formatter: value => `<a href="/admin/post/${value}">${value}</a>`
            },
            {
                title: '제목',
                name: 'title',
                formatter: (value, row) => `<a href="/admin/post/${row.no}">${value}</a>`
            },
            {
                title: '태그',
                name: 'tags',
                width: 100,
                formatter: value => value.map(tag => tag.name).join(',')
            },
            {
                title: '수정일',
                name: 'updateYmdt',
                width: 150,
                align: 'center',
                formatter: value => blog.common.getDateInKR(value)
            },
            {
                title: '등록일',
                name: 'registerYmdt',
                width: 150,
                align: 'center',
                formatter: value => blog.common.getDateInKR(value)
            }
        ];
    }
};

