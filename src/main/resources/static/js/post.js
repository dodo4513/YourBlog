$(() => {
    // const URI = {POST: '/posts'};

    blog.post = {
        grid: null,
        init() {
            this.initGrind();
        },
        initGrind() {
            const data = [
                {
                    id: '10012',
                    city: 'Seoul',
                    country: 'South Korea'
                },
                {
                    id: '10013',
                    city: 'Tokyo',
                    country: 'Japan'
                },
                {
                    id: '10014',
                    city: 'London',
                    country: 'England'
                },
                {
                    id: '10015',
                    city: 'Ljubljana',
                    country: 'Slovenia'
                },
                {
                    id: '10016',
                    city: 'Reykjavik',
                    country: 'Iceland'
                }
            ];

            // case 1 : using data option
            const grid = new tui.Grid({
                el: $('#wrapper'),
                data
            });

            grid.setColumns([
                {
                    title: 'ID',
                    name: 'id'
                },
                {
                    title: 'CITY',
                    name: 'city',
                    editOptions: {
                        type: 'text'
                    }
                },
                {
                    title: 'COUNTRY',
                    name: 'country',
                    editOptions: {
                        type: 'checkbox'
                    }
                }
            ]);

            // case 2 : using setData method
            grid.setData(data);
        }
    };

    blog.post.init();
});
